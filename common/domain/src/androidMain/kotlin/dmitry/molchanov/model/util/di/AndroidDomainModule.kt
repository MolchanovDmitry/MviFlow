package dmitry.molchanov.model.util.di

import dmitry.molchanov.util.Dispatchers as AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val androidDomainModule = module{
    single<AppDispatchers>{
        object : AppDispatchers {
            override val main: CoroutineDispatcher = Dispatchers.Main.immediate
            override val io: CoroutineDispatcher = Dispatchers.IO
            override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
        }
    }
}