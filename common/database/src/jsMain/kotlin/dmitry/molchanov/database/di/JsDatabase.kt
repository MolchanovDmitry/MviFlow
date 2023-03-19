package dmitry.molchanov.database.di

import dmitry.molchanov.database.AppDatabase
import dmitry.molchanov.database.DriverFactory
import dmitry.molchanov.database.TodoItemDataStoreImpl
import dmitry.molchanov.model.TodoItemDataStore
import org.koin.dsl.module

val jsDatabase = module {
    /*single<AppDatabase> {
         AppDatabase(DriverFactory().createDriver())
    }*/

    single<TodoItemDataStore> { TodoItemDataStoreImpl() }
}