package com.quoctran.trackme.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import com.quoctran.trackme.MainActivity
import com.quoctran.trackme.R
import com.quoctran.trackme.data.WorkoutPreference
import com.quoctran.trackme.util.getLocationText
import com.quoctran.trackme.util.getLocationTitle

class WorkoutService : Service() {

    companion object {
        private const val PACKAGE_NAME = "com.quoctran.trackme.service.WorkoutService"
        private const val TAG = "WorkoutService"
        private const val CHANNEL_ID = "channel_workout"
        private const val EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
                ".started_from_notification"
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
        private const val NOTIFICATION_ID = 10010
        const val ACTION_BROADCAST = "$PACKAGE_NAME.broadcast"
        const val EXTRA_LOCATION = "$PACKAGE_NAME.location"

        private lateinit var mNotificationManager: NotificationManager
        private lateinit var mLocationRequest: LocationRequest
        private lateinit var mFusedLocationClient: FusedLocationProviderClient
        private lateinit var mLocationCallback: LocationCallback
        private lateinit var mServiceHandler: Handler
        private var mLocation: Location? = null
        private var mChangingConfiguration: Boolean = false
    }
    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        stopForeground(true)
        return mBinder;
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if(WorkoutPreference.isLocationUpdate!!){
            startForeground(NOTIFICATION_ID, getNotification()
            )
        }
        return true
    }

    override fun onRebind(intent: Intent?) {
        stopForeground(true)
        mChangingConfiguration = false
        super.onRebind(intent)
    }

    override fun onCreate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                onNewLocation(locationResult.lastLocation)
            }
        }

        createLocationRequest()
        getLastLocation()
        val handlerThread =
            HandlerThread(TAG)
        handlerThread.start()
        mServiceHandler = Handler(handlerThread.looper)
        mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(com.quoctran.trackme.R.string.app_name)
            // Create the channel for the notification
            val mChannel = NotificationChannel(
                CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startedFromNotification =
            intent!!.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false)
        if (startedFromNotification) {
            removeLocationUpdates()
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mServiceHandler.removeCallbacksAndMessages(this)
        super.onDestroy()
    }

    private fun getNotification(): Notification? {
        val intent = Intent(
            this, WorkoutService::class.java
        )
        val text: CharSequence = mLocation?.getLocationText().toString()

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION,true
        )

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        val servicePendingIntent = PendingIntent.getService(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // The PendingIntent to launch activity.
        val activityPendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), 0
        )
        val builder = NotificationCompat.Builder(this)
            .addAction(
                R.drawable.ic_launcher_foreground, getString(R.string.launch_app),
                activityPendingIntent
            )
            .addAction(
                R.drawable.ic_launcher_foreground, getString(R.string.remove_workout),
                servicePendingIntent
            )
            .setContentText(text)
            .setContentTitle(mLocation?.getLocationTitle(this))
            .setOngoing(true)
            .setPriority(Notification.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(text)
            .setWhen(System.currentTimeMillis())

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }
        return builder.build()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun removeLocationUpdates() {
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
            WorkoutPreference.isLocationUpdate = false
            stopSelf()
        } catch (unlikely: SecurityException) {
            WorkoutPreference.isLocationUpdate = true
        }
    }

    private fun onNewLocation(location: Location) {
        mLocation = location

        val intent = Intent(ACTION_BROADCAST)
        intent.putExtra( EXTRA_LOCATION, location
        )
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        if (serviceIsRunningInForeground(this)) {
            mNotificationManager.notify(NOTIFICATION_ID, getNotification()
            )
        }
    }

    private fun getLastLocation() {
        try {
            mFusedLocationClient.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLocation = task.result
                    }
                }
        } catch (unlikely: SecurityException) {

        }
    }

    private fun serviceIsRunningInForeground(context: Context): Boolean {
        val manager = context.getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (javaClass.name == service.service.className) {
                if (service.foreground) {
                    return true
                }
            }
        }
        return false
    }

    inner class LocalBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
}