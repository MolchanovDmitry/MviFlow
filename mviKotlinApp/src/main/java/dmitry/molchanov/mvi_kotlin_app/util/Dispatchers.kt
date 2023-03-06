package dmitry.molchanov.mvi_kotlin_app.util

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}