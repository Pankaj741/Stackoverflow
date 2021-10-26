package com.example.stackoverflow.ui.questionlist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.stackoverflow.MyApplication
import com.example.stackoverflow.R
import com.example.stackoverflow.databinding.ActivityQuestionsBinding
import com.example.stackoverflow.di.components.DaggerActivityComponent
import com.example.stackoverflow.di.modules.ActivityModule

class QuestionsActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment_question) }
    private val binding by lazy { ActivityQuestionsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController.setGraph(R.navigation.nav_graph, intent.extras)
        getDependencies()
    }

    private fun getDependencies() {
        DaggerActivityComponent
            .builder()
            .activityModule(ActivityModule(this)) // this is shown as deprecated as no instance provided by it is being injected
            .applicationComponent((application as MyApplication).applicationComponent)
            .build()
            .inject(this)
    }
}