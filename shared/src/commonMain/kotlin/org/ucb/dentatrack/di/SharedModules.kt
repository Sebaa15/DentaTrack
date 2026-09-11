package org.ucb.dentatrack.di
import org.koin.core.module.Module

fun sharedModules(): List<Module> = listOf(
    dataModule,
    domainModule,
    presentationModule
)