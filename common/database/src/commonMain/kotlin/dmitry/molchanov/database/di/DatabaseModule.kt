package dmitry.molchanov.database.di

import dmitry.molchanov.database.AppDatabase
import dmitry.molchanov.database.TodoItemDataStoreImpl
import dmitry.molchanov.db.TodoItemTableQueries
import dmitry.molchanov.model.TodoItemDataStore
import org.koin.dsl.module

val databaseModule = module {

    single<TodoItemDataStore> { TodoItemDataStoreImpl(get()) }


    single<TodoItemTableQueries> { get<AppDatabase>().todoItemTableQueries }
}