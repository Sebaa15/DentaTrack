package org.ucb.dentatrack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform