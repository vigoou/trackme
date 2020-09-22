package com.quoctran.trackme.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.quoctran.trackme.R

class WorkoutFragment :Fragment(){

//    private lateinit var viewDataBinding: TasksFragBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    companion object{
        fun newInstance() = WorkoutFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}