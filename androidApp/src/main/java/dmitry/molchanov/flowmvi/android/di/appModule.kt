package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.presentation.DetailsViewModelPlatformImpl
import dmitry.molchanov.presentation.MainViewModelPlatformImpl
import dmitry.molchanov.presentation.details.DetailsViewModel
import dmitry.molchanov.presentation.details.DetailsViewModelImpl
import dmitry.molchanov.presentation.main.MainViewModel
import dmitry.molchanov.presentation.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModelPlatformImpl(get()) }

    factory<MainViewModel> { MainViewModelImpl(get(), get(), get(), get(), get()) }

    viewModel { DetailsViewModelPlatformImpl(get()) }

    factory<DetailsViewModel> { params -> DetailsViewModelImpl(params.get(), get(), get(), get(), get()) }
}
