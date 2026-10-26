package org.ucb.dentatrack.sheduler

interface OdontogramWorkScheduler {
    fun schedule(userId: String)
    fun cancel()
}