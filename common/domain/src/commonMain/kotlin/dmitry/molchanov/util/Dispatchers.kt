package dmitry.molchanov.util

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}