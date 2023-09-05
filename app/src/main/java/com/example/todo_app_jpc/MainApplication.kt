package com.example.todo_app_jpc

import android.app.Application
import com.example.todo_app_jpc.ItemData.AppContainer
import com.example.todo_app_jpc.ItemData.AppDataContainer


//@HiltAndroidApp
class MainApplication: Application(){
    // 他のクラスが依存関係を取得するために使用するAppContainerインスタンス

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}