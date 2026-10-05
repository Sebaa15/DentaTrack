package org.ucb.dentatrack.database

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

fun getDatabaseBuilder(
    context: Context
): RoomDatabase.Builder<AppDatabase> {

    val dbFile =
        context.applicationContext
            .getDatabasePath("dentatrack.db")

    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}