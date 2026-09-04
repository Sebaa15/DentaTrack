package org.ucb.dentatrack

import android.app.Application
import org.ucb.dentatrack.di.initKoinAndroid

class MainApplication: Application()
{
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)
    }
}