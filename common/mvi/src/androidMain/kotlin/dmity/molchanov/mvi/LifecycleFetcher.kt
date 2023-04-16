package dmity.molchanov.mvi

import androidx.lifecycle.Lifecycle

fun interface LifecycleFetcher {

    fun fetchLifecycle(): Lifecycle
}

val LifecycleFetcher.lifecycle: Lifecycle
    get() = fetchLifecycle()