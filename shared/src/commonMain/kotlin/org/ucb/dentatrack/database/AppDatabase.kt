package org.ucb.dentatrack.database

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.ucb.dentatrack.Login.Data.Dao.LoggedUserDao
import org.ucb.dentatrack.Login.Data.Entity.LoggedUserEntity
import org.ucb.dentatrack.Odontogram.Data.Dao.ToothDao
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity
import org.ucb.dentatrack.User.Data.Dao.UserDao
import org.ucb.dentatrack.User.Data.Entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ToothEntity::class
    ],
    version = 1
)
@ConstructedBy(
    AppDatabaseConstructor::class
)
abstract class AppDatabase :
    RoomDatabase() {

    abstract fun userDao():
            UserDao

    abstract fun toothDao():
            ToothDao
}

@Suppress(
    "KotlinNoActualForExpect"
)
expect object AppDatabaseConstructor :
    RoomDatabaseConstructor<AppDatabase> {

    override fun initialize():
            AppDatabase
}

fun getRoomDatabase(
    builder:
    RoomDatabase.Builder<AppDatabase>
): AppDatabase {

    return builder
        .setDriver(
            BundledSQLiteDriver()
        )
        .setQueryCoroutineContext(
            Dispatchers.IO
        )
        .build()
}