package com.example.todo_app_jpc.ItemData

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer{
    /**
     * Implementation for [ItemsRepository]
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//class Modules {
////    @Provides
////    @Singleton
////    fun provideItemRepository(context: Context): ItemsRepository {
////        return OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
////    }
//
//    @Provides
//    @Singleton
//    fun provideContext(application: Application): Context {
//        return application.applicationContext
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideItemsRepository(provideContext: Context): ItemsRepository {
//        return OfflineItemsRepository(InventoryDatabase.getDatabase(provideContext).itemDao())
//    }
//}