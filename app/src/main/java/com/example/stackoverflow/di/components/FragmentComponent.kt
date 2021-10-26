package com.example.stackoverflow.di.components

import com.example.stackoverflow.di.modules.FragmentModule
import com.example.stackoverflow.di.scope.FragmentScope
import com.example.stackoverflow.ui.questionlist.view.QuestionListFragment
import dagger.Component

@FragmentScope
@Component(
    modules = [FragmentModule::class],
    dependencies = [ApplicationComponent::class]
)
interface FragmentComponent {

    fun inject(questionListFragment: QuestionListFragment)

}