package dmitry.molchanov.database

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {

     fun createDriver(): SqlDriver
}