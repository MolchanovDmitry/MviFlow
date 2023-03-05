package dmitry.molchanov.mvi_kotlin_app

import android.app.Application

class App : Application() {

    //lateinit var database: TodoDatabase
        //private set

    //private val timeTravelServer = TimeTravelServer()

    override fun onCreate() {
        super.onCreate()

        //database = DefaultTodoDatabase(context = this)
        //timeTravelServer.start()
    }
}
