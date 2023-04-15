package dmitry.molchanov.flowmvi.android.di

import dmitry.molchanov.flowmvi.android.main.MainController
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

val mviMainModule = module {

    factory {
        MainController(
            lifecycle = it.get(),
            lifecycleOwner = it.get(),
            viewModel = get(),
            dispatchers = get(),
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


