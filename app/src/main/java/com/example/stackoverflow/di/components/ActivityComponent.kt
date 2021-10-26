package com.example.stackoverflow.di.components

import com.example.stackoverflow.di.modules.ActivityModule
import com.example.stackoverflow.di.scope.ActivityScope
import com.example.stackoverflow.ui.questionlist.view.QuestionsActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [ActivityModule::class],
    dependencies = [ApplicationComponent::class]
)
interface ActivityComponent {

    fun inject(questionsActivity: QuestionsActivity)
}