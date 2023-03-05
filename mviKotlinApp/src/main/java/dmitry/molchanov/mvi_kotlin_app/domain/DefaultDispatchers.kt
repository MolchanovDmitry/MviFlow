package dmitry.molchanov.mvi_kotlin_app.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Suppress("InjectDispatcher")
object DefaultDispatchers : TodoDispatchers {

    override val main: CoroutineDispatcher get() = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher get() = Dispatchers.IO
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}
