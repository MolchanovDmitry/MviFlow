package dmitry.molchanov.mvi_kotlin_app.domain

import kotlinx.coroutines.CoroutineDispatcher

interface TodoDispatchers {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
