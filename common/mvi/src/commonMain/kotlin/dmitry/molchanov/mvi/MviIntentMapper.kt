package dmitry.molchanov.mvi

interface MviIntentMapper<Event, Intent> {

    val map: (Event) -> Intent
}