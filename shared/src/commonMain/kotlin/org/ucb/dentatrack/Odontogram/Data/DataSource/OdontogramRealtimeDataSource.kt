package org.ucb.dentatrack.Odontogram.Data.DataSource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OdontogramRealtimeDataSource {

    private val database by lazy {
        Firebase.database
    }

    private val messageRef by lazy {
        database.reference(
            "dentatrack_test"
        )
    }

    fun observeMessage():
            Flow<String?> {

        return messageRef
            .valueEvents
            .map { snapshot ->

                snapshot
                    .value<String?>()
            }
    }
}