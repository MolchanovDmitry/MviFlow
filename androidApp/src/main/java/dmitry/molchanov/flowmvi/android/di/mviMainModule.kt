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
            viewModel = params.get(),
            onItemClick = params.get(),
            lifecycleFetcher = params.get(),
            dispatchers = get(),
            mainScreenEventHandler = MainScreenIntentMapper(),
        )
    }

    factory { params ->
        DetailsController(
            detailsViewModel = get<DetailsViewModel> {
                parametersOf(params.get())
            },
            dispatchers = get(),
            detailsViewEventHandler = DetailsViewEventHandler(),
            lifecycleFetcher = params.get(),
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


