package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.flowmvi.android.details.DetailsController
import dmitry.molchanov.flowmvi.android.details.DetailsViewEventHandler
import dmitry.molchanov.flowmvi.android.main.MainController
import dmitry.molchanov.flowmvi.android.main.MainScreenIntentMapper
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
            detailsViewModel = params.get(),
            dispatchers = get(),
            detailsViewEventHandler = DetailsViewEventHandler(),
            lifecycleFetcher = params.get(),
            onItemDeleted = params.get(),
        )
    }


}

