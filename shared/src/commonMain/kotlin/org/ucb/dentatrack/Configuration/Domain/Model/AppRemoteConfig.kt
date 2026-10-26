package org.ucb.dentatrack.Configuration.Domain.Model

data class AppRemoteConfig(

    val odontogramTitle:
    String = "MI ODONTOGRAMA",

    val enableTreatmentDetail:
    Boolean = true,

    val maintenanceMode:
    Boolean = false,

    val notificationsEnabled:
    Boolean = true,

    val surveyEnabled:
    Boolean = false,

    val minimumSupportedVersion:
    Long = 1,

    val latestVersion:
    String = "1.0.0"
)