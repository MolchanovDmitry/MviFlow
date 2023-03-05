package dmitry.molchanov.di

import dmitry.molchanov.usecase.AddTodoItemUseCase
import dmitry.molchanov.usecase.EditTodoItemUseCase
import dmitry.molchanov.usecase.GetTodoItemsUseCase
import dmitry.molchanov.usecase.RemoveTodoItemUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { AddTodoItemUseCase(get(), get()) }
    factory { GetTodoItemsUseCase(get(), get()) }
    factory { RemoveTodoItemUseCase(get(), get()) }
    factory { EditTodoItemUseCase(get(), get()) }
}