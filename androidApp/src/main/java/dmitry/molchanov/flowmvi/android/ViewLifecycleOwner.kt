package dmitry.molchanov.flowmvi.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

interface ViewLifecycleOwner {

    fun getViewLifecycleOwner(): LifecycleOwner
}

val ViewLifecycleOwner.lifecycle: Lifecycle
    get() = getViewLifecycleOwner().lifecycle
