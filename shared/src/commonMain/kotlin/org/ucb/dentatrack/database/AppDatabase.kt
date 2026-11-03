package org.ucb.dentatrack.database

import org.ucb.dentatrack.Care.Data.Dao.CareDao
import org.ucb.dentatrack.Care.Data.Entity.CareItemEntity
import org.ucb.dentatrack.Care.Data.Entity.CareCheckEntity
import org.ucb.dentatrack.Appointments.Data.Dao.AppointmentDao
import org.ucb.dentatrack.Appointments.Data.Entity.AppointmentEntity
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
        TreatmentStepEntity::class,
        AppointmentEntity::class,
        CareItemEntity::class,
        CareCheckEntity::class
    ],
    version = 6
)
@ConstructedBy(
    AppDatabaseConstructor::class
)
abstract class AppDatabase :
    RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun toothDao(): ToothDao
    abstract fun treatmentStepDao(): TreatmentStepDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun careDao(): CareDao
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
            MIGRATION_3_4,
            MIGRATION_4_5,
            MIGRATION_5_6
        )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}