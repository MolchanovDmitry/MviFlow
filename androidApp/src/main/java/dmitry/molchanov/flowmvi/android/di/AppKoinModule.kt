package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.presentation.DetailsVM
import dmitry.molchanov.presentation.MainVM
import dmitry.molchanov.presentation.di.presentationModule
import dmitry.molchanov.presentation.main.MainViewModel
import dmitry.molchanov.presentation.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    includes(controllerModule)

    includes(presentationModule)

    factory<MainViewModel> {
        MainViewModelImpl(
            get(), get(), get(), get(), get()
        )
    }

    viewModel { params -> DetailsVM(get(parameters = { params })) }

    viewModel { MainVM(get()) }
}
