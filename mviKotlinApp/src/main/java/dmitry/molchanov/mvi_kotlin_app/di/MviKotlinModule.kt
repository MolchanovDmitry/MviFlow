package dmitry.molchanov.mvi_kotlin_app.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import dmitry.molchanov.model.TodoItemDataStore
import dmitry.molchanov.mvi_kotlin_app.domain.DefaultDispatchers
import dmitry.molchanov.mvi_kotlin_app.domain.TodoDispatchers
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStoreFactory
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStoreFactory
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStoreFactory
import dmitry.molchanov.mvi_kotlin_app.storeFactoryInstance
import org.koin.dsl.module

val mviKotlinModule = module {

    factory<TodoDispatchers> {
        DefaultDispatchers
    }

    factory<StoreFactory> {
        storeFactoryInstance
    }

    factory { params ->
        DetailsStoreFactory(
            storeFactory = get(),
            mainContext = get<TodoDispatchers>().main,
            ioContext = get<TodoDispatchers>().io,
            dataStore = get(),
            itemId = params.get()
        ).create()
    }

    factory {
        ListStoreFactory(
            storeFactory = get(),
            mainContext = get<TodoDispatchers>().main,
            ioContext = get<TodoDispatchers>().io,
            dataStore = get()
        ).create()
    }

    factory {
        AddStoreFactory(
            storeFactory = get(),
            mainContext = get<TodoDispatchers>().main,
            ioContext = get<TodoDispatchers>().io,
            dataStore = get()
        ).create()
    }

    factory<ListStore> {
        ListStoreFactory(
            storeFactory = get(),
            mainContext = get<TodoDispatchers>().main,
            ioContext = get<TodoDispatchers>().io,
            dataStore = get()
        ).create()
    }

    factory { storeFactoryInstance }

}