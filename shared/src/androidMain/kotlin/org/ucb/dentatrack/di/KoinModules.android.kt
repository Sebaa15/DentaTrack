package org.ucb.dentatrack.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.ucb.dentatrack.Shared.di.sharedModules

fun initKoinAndroid(context:Context){
    startKoin{
        androidContext(context)
        androidLogger()
        modules(sharedModules())

    }
}
