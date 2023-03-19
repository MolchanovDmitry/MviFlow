package dmitry.molchanov.presentation.di

import dmitry.molchanov.presentation.details.DetailsViewModel
import dmitry.molchanov.presentation.details.DetailsViewModelImpl
import dmitry.molchanov.presentation.main.MainViewModel
import dmitry.molchanov.presentation.main.MainViewModelImpl
import org.koin.dsl.module

val presentationModule = module {

    factory<MainViewModel> { MainViewModelImpl(get(), get(), get(), get(), get()) }

    factory<DetailsViewModel> { params ->
        DetailsViewModelImpl(
            itemId = params.get(),
            dispatchers = get(),
            getTodoItemsUseCase = get(),
            editTodoUseCase = get(),
            removeTodoItemUseCase = get()
        )
    }
}
