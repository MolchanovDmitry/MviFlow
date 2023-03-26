package dmitry.molchanov.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise

actual class DriverFactory {

    actual fun createDriver(): SqlDriver {
        return runBlocking<SqlDriver> { initSqlDriver(AppDatabase.Schema).await() } as SqlDriver
    }

    private fun <T> runBlocking(
        block: suspend CoroutineScope.() -> T
    ): dynamic = GlobalScope.promise { block() }

}