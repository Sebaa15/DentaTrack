package org.ucb.dentatrack.database

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.ucb.dentatrack.ExchangeRate.Data.dao.ExchangeRateDao
import org.ucb.dentatrack.ExchangeRate.Data.entity.ExchangeRateEntity

@Database(
    entities=[ExchangeRateEntity::class],version=1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun exchangeRateDao(): ExchangeRateDao
}
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor:RoomDatabaseConstructor<AppDatabase>{
    override fun initialize(): AppDatabase
}
fun getRoomDatabase(builder:RoomDatabase.Builder<AppDatabase>):AppDatabase{
    return builder.setDriver(BundledSQLiteDriver()).
            setQueryCoroutineContext(Dispatchers.IO).
            build()
}