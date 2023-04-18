package dmitry.molchanov.mvi_kotlin_app.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import dmitry.molchanov.mvi_kotlin_app.domain.details.store.DetailsStoreFactory
import dmitry.molchanov.mvi_kotlin_app.domain.main.MainController
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.AddStoreFactory
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStore
import dmitry.molchanov.mvi_kotlin_app.domain.main.store.ListStoreFactory
import dmitry.molchanov.mvi_kotlin_app.storeFactoryInstance
import dmitry.molchanov.util.Dispatchers
import org.koin.dsl.module

val mviKotlinModule = module {

    factory<StoreFactory> {
        storeFactoryInstance
    }

    factory { params ->
        DetailsStoreFactory(
            storeFactory = get(),
            mainContext = get<Dispatchers>().main,
            itemId = params.get(),
            editTodoItemUseCase = get(),
            getTodoItemsUseCase = get(),
            removeTodoItemUseCase = get(),

            ).create()
    }

    factory {
        ListStoreFactory(
            storeFactory = get(),
            mainContext = get<Dispatchers>().main,
            getTodoItemsUseCase = get(),
            editTodoItemUseCase = get(),
            removeTodoItemUseCase = get(),

            ).create()
    }

    factory {
        AddStoreFactory(
            storeFactory = get(),
            mainContext = get<Dispatchers>().main,
            addTodoItemUseCase = get(),
        ).create()
    }

    factory<ListStore> {
        ListStoreFactory(
            storeFactory = get(),
            mainContext = get<Dispatchers>().main,
            getTodoItemsUseCase = get(),
            editTodoItemUseCase = get(),
            removeTodoItemUseCase = get(),
        ).create()
    }

    factory { storeFactoryInstance }

    factory { params ->
        MainController(
            lifecycle = params.get(),
            onItemSelected = params.get(),
            dispatchers = get(),
            listStore = get(),
            addStore = params.get()
        )
    }

}