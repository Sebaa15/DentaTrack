package org.ucb.dentatrack.Shared.di
import org.koin.core.module.Module

fun sharedModules(): List<Module> = listOf(
    dataModule,
    domainModule,
    presentationModule
)