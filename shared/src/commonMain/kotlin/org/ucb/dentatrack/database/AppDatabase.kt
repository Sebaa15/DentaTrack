package org.ucb.dentatrack.database

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.ucb.dentatrack.Notifications.Data.Dao.NotificationDao
import org.ucb.dentatrack.Odontogram.Data.Dao.ToothDao
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity
import org.ucb.dentatrack.User.Data.Dao.UserDao
import org.ucb.dentatrack.User.Data.Entity.UserEntity
import org.ucb.dentatrack.Notifications.Data.Entity.NotificationEntity

@Database(
    entities = [
        UserEntity::class,
        ToothEntity::class,
        NotificationEntity::class
    ],
    version = 4
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
    abstract fun notificationDao():
            NotificationDao
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
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {

    return builder
        .addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4
        )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}