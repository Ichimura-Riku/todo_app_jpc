package com.example.todo_app_jpc

import android.app.Application

import com.example.todo_app_jpc.data.TodoAppContainer
import com.example.todo_app_jpc.data.TodoAppDataContainer
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApplication: Application(){
    // 他のクラスが依存関係を取得するために使用するAppContainerインスタンス

//    lateinit var container: AppContainer
    lateinit var todoContainer: TodoAppContainer
    override fun onCreate() {
        super.onCreate()
//        container = AppDataContainer(this)
        todoContainer = TodoAppDataContainer(this)
    }

}