package dmitry.molchanov.mvi

/**
 * Получатор событий от вью модели.
 */
interface Reducer<Message, State>{

    /** Смапить сообщение от вью модели в состояние */
    fun reduce(currentState: State, msg: Message): State
}