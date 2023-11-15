package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.flowmvi.android.details.DetailsController
import dmitry.molchanov.flowmvi.android.details.DetailsViewEventHandler
import dmitry.molchanov.flowmvi.android.main.MainController
import dmitry.molchanov.flowmvi.android.main.MainScreenIntentMapper
import dmitry.molchanov.presentation.DetailsVM
import dmity.molchanov.mvi.fetchViewModel
import org.koin.dsl.module

val controllerModule = module {

    factory { params ->
        MainController(
            viewModel = params.fetchViewModel(),
            onItemClick = params.get(),
            lifecycleFetcher = params.get(),
            dispatchers = get(),
            mainScreenEventHandler = MainScreenIntentMapper(),
        )
    }

    factory { params ->
        DetailsController(
            detailsViewModel = params.fetchViewModel(),
            dispatchers = get(),

            detailsViewEventHandler = DetailsViewEventHandler(),
            lifecycleFetcher = params.get(),
            onItemDeleted = params.get(),
        )
    }

}

