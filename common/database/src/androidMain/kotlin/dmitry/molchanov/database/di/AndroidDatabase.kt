package dmitry.molchanov.database.di

import dmitry.molchanov.database.AppDatabase
import dmitry.molchanov.database.DriverFactory
import org.koin.dsl.module

val androidDatabase = module {
    single<AppDatabase> {
        AppDatabase(DriverFactory(context = get()).createDriver())
    }
}