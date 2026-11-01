package org.ucb.dentatrack.database

import org.ucb.dentatrack.TreatmentPlan.Data.Dao.TreatmentStepDao
import org.ucb.dentatrack.TreatmentPlan.Data.Entity.TreatmentStepEntity
import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.ucb.dentatrack.Odontogram.Data.Dao.ToothDao
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity
import org.ucb.dentatrack.User.Data.Dao.UserDao
import org.ucb.dentatrack.User.Data.Entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ToothEntity::class,
        TreatmentStepEntity::class
    ],
    version = 4
)
@ConstructedBy(
    AppDatabaseConstructor::class
)
abstract class AppDatabase :
    RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun toothDao(): ToothDao
    abstract fun treatmentStepDao(): TreatmentStepDao
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