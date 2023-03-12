package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.presentation.DetailsVM
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.details.DetailsViewModel
import dmitry.molchanov.presentation.details.DetailsViewModelImpl
import dmitry.molchanov.presentation.main.MainViewModel
import dmitry.molchanov.presentation.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainVM(get()) }

    factory<MainViewModel> { MainViewModelImpl(get(), get(), get(), get(), get()) }

    viewModel { params -> DetailsVM(get(parameters = { params })) }

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
