package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.flowmvi.android.main.MainController
import dmitry.molchanov.flowmvi.android.main.MainScreenIntentMapper
import org.koin.dsl.module

val mviMainModule = module {

    factory { params ->
        MainController(
            lifecycle = params.get(),
            lifecycleOwner = params.get(),
            viewModel = get(),
            dispatchers = get(),
            onItemClick = params.get(),
            mainScreenEventHandler = MainScreenIntentMapper(),
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


