package com.example.todoAppJpc

import android.app.Application
import com.example.todoAppJpc.data.TodoAppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    // 他のクラスが依存関係を取得するために使用するAppContainerインスタンス

    //    lateinit var container: AppContainer
    // イラン
    lateinit var todoContainer: TodoAppContainer
    override fun onCreate() {
        super.onCreate()
//        container = AppDataContainer(this)
//        todoContainer = TodoAppDataContainer(this)
    }
}
