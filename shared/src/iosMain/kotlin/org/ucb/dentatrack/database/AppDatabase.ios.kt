package org.ucb.dentatrack.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder():
        RoomDatabase.Builder<AppDatabase> {

    val dbPath =
        documentDirectory() +
                "/dentatrack.db"

    return Room.databaseBuilder<AppDatabase>(
        name = dbPath
    )
}

private fun documentDirectory():
        String {

    val directory =
        NSFileManager.defaultManager
            .URLForDirectory(
                directory =
                    NSDocumentDirectory,
                inDomain =
                    NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )

    return requireNotNull(
        directory?.path
    )
}