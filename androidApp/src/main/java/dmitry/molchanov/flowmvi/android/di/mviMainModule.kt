package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.flowmvi.android.details.DetailsController
import dmitry.molchanov.flowmvi.android.details.DetailsViewEventHandler
import dmitry.molchanov.flowmvi.android.main.MainController
import dmitry.molchanov.flowmvi.android.main.MainScreenIntentMapper
import dmitry.molchanov.presentation.details.DetailsViewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val mviMainModule = module {

    factory { params ->
        MainController(
            lifecycle = params.get(),
            viewModel = params.get(),
            dispatchers = get(),
            onItemClick = params.get(),
            mainScreenEventHandler = MainScreenIntentMapper(),
        )
    }

    factory { params ->
        DetailsController(
            lifecycle = params.get(),
            detailsViewModel = get<DetailsViewModel> {
                parametersOf(params.get())
            },
            dispatchers = get(),
            detailsViewEventHandler = DetailsViewEventHandler(),
        )
    }


}

/*
fun <T> mviFactory(
    qualifier: Qualifier? = null,
    definition: Definition<T>
): KoinDefinition<T> {
    return org.koin.core.module.factory(qualifier, definition)
}

*/


