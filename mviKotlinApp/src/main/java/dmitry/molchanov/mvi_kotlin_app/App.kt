package dmitry.molchanov.mvi_kotlin_app

import android.app.Application
import dmitry.molchanov.database.di.androidDatabase
import dmitry.molchanov.database.di.databaseModule
import dmitry.molchanov.mvi_kotlin_app.di.mviKotlinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    //lateinit var database: TodoDatabase
    //private set

    //private val timeTravelServer = TimeTravelServer()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                mviKotlinModule,
                androidDatabase,
                databaseModule
            )
        }

        //database = DefaultTodoDatabase(context = this)
        //timeTravelServer.start()
    }
}
