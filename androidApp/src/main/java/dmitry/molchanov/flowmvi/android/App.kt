package dmitry.molchanov.flowmvi.android

import android.app.Application
import dmitry.molchanov.database.di.androidDatabase
import dmitry.molchanov.database.di.databaseModule
import dmitry.molchanov.di.domainModule
import dmitry.molchanov.flowmvi.android.di.appModule
import dmitry.molchanov.model.util.di.androidDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                domainModule,
                androidDomainModule,
                androidDatabase,
                databaseModule
            )
        }
    }
}