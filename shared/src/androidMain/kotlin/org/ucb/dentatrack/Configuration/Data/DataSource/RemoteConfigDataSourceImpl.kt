package org.ucb.dentatrack.Configuration.Data.DataSource


import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.android
import dev.gitlive.firebase.remoteconfig.get
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Configuration.Domain.Model.AppRemoteConfig
import kotlin.time.Duration.Companion.seconds

class RemoteConfigDataSourceImpl :
    RemoteConfigDataSource {

    private val config by lazy {
        Firebase.remoteConfig
    }

    override fun observeConfig():
            Flow<AppRemoteConfig> =
        callbackFlow {

            launch {

                config.settings {
                    minimumFetchInterval =
                        0.seconds
                }

                config.setDefaults(

                    "odontogram_title" to
                            "MI ODONTOGRAMA",

                    "enable_treatment_detail" to
                            true,

                    "maintenance_mode" to
                            false,

                    "notifications_enabled" to
                            true,

                    "survey_enabled" to
                            false,

                    "minimum_supported_version" to
                            1L,

                    "latest_version" to
                            "1.0.0"
                )

                config.fetchAndActivate()

                trySend(
                    readConfig()
                )
            }

            val listener =
                config.android
                    .addOnConfigUpdateListener(
                        object :
                            ConfigUpdateListener {

                            override fun onUpdate(
                                update: ConfigUpdate
                            ) {

                                launch {

                                    config.activate()

                                    trySend(
                                        readConfig()
                                    )
                                }
                            }

                            override fun onError(
                                error:
                                FirebaseRemoteConfigException
                            ) {
                                close(error)
                            }
                        }
                    )

            awaitClose {
                listener.remove()
            }
        }

    private fun readConfig():
            AppRemoteConfig {

        return AppRemoteConfig(

            odontogramTitle =
                config["odontogram_title"],

            enableTreatmentDetail =
                config["enable_treatment_detail"],

            maintenanceMode =
                config["maintenance_mode"],

            notificationsEnabled =
                config["notifications_enabled"],

            surveyEnabled =
                config["survey_enabled"],

            minimumSupportedVersion =
                config["minimum_supported_version"],

            latestVersion =
                config["latest_version"]
        )
    }
}