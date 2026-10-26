
package org.ucb.dentatrack

import android.app.Application
import org.ucb.dentatrack.di.initKoinAndroid
import org.ucb.dentatrack.Notifications.NotificationPreferences
import org.ucb.dentatrack.Notifications.schedulePendingNotifications

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(this)

        NotificationPreferences.initialize()

        schedulePendingNotifications(this)
    }
}

