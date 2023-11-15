package dmitry.molchanov.presentation.di

import dmitry.molchanov.presentation.main.MainViewModelReducer
import dmitry.molchanov.presentation.details.DetailsViewModelImpl
import dmitry.molchanov.presentation.details.DetailsViewModelReducer
import dmitry.molchanov.presentation.main.MainViewModelImpl
import org.koin.dsl.module

val presentationModule = module {

    factory { MainViewModelReducer() }

    factory { MainViewModelImpl(get(), get(), get(), get(), get(), get()) }

    factory { DetailsViewModelReducer() }

    factory { params ->
        DetailsViewModelImpl(
            itemId = params.get(),
            reducer = get(),
            dispatchers = get(),
            getTodoItemsUseCase = get(),
            editTodoUseCase = get(),
            removeTodoItemUseCase = get()
        )
    }
}
