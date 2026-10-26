package org.ucb.dentatrack.database

import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {

    override suspend fun migrate(
        connection: SQLiteConnection
    ) {

        // Crear la tabla con la estructura de la versión 2
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `users_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `fullName` TEXT NOT NULL,
                `email` TEXT NOT NULL,
                `password` TEXT NOT NULL,
                `isLoggedIn` INTEGER NOT NULL
            )
            """.trimIndent()
        )

        // Conservar usuarios existentes
        // y comenzar con la sesión cerrada
        connection.execSQL(
            """
            INSERT INTO `users_new`
                (`id`, `fullName`, `email`, `password`, `isLoggedIn`)
            SELECT
                `id`, `fullName`, `email`, `password`, 0
            FROM `users`
            """.trimIndent()
        )

        // Sustituir la tabla antigua
        connection.execSQL(
            "DROP TABLE `users`"
        )

        connection.execSQL(
            "ALTER TABLE `users_new` RENAME TO `users`"
        )

        // Restaurar el índice único del correo
        connection.execSQL(
            """
            CREATE UNIQUE INDEX IF NOT EXISTS `index_users_email`
            ON `users` (`email`)
            """.trimIndent()
        )
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {

    override suspend fun migrate(connection: SQLiteConnection) {

        connection.execSQL("""
            CREATE TABLE IF NOT EXISTS `teeth_new` (
                `userId` INTEGER NOT NULL,
                `number` INTEGER NOT NULL,
                `status` TEXT NOT NULL,
                `treatment` TEXT,
                PRIMARY KEY(`userId`, `number`)
            )
        """.trimIndent())

        connection.execSQL("""
            INSERT INTO `teeth_new`
                (`userId`, `number`, `status`, `treatment`)
            SELECT
                COALESCE(
                    (SELECT id FROM users WHERE isLoggedIn = 1 LIMIT 1),
                    0
                ),
                number, status, treatment
            FROM `teeth`
        """.trimIndent())

        connection.execSQL("DROP TABLE `teeth`")
        connection.execSQL("ALTER TABLE `teeth_new` RENAME TO `teeth`")
    }
}
val MIGRATION_3_4 = object : Migration(3, 4) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `notification_history` (
                `messageId` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `body` TEXT NOT NULL,
                `receivedAt` INTEGER NOT NULL,
                `displayed` INTEGER NOT NULL,
                `displayedAt` INTEGER,
                PRIMARY KEY(`messageId`)
            )
            """.trimIndent()
        )
    }
}
