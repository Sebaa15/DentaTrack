import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.util.zip.ZipInputStream

plugins {
    alias(libs.plugins.wire)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
}
kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    android {
        namespace = "org.ucb.dentatrack.shared"

        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }

        androidResources {
            enable = true
        }

        withHostTest {
            isIncludeAndroidResources = true
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.okhttp)
            implementation(libs.androidx.work.runtime.ktx)
            implementation(libs.androidx.core.ktx)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(libs.multiplatform.settings)
            implementation(libs.firebase.auth)
            implementation(libs.wire.runtime)
            implementation(libs.wire.grpc.client)
            implementation(libs.firebase.firestore)
            implementation("edu.ucb.ds:shared:1.0.3-SNAPSHOT")
            implementation(libs.firebase.database)
            implementation(libs.firebase.config)
            implementation(libs.firebase.messaging)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.room3.runtime)
            implementation(libs.androidx.sqlite.bundled)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
wire {
    sourcePath {
        srcDir("src/commonMain/proto")
    }
    kotlin {
        rpcRole = "client"
    }
}
room3 {
    schemaDirectory("$projectDir/schemas")
}

val locoApiKey = providers.gradleProperty("LOCO_API_KEY")

tasks.register("downloadLocaliseTranslations") {

    group = "localization"
    description = "Descarga traducciones de DentaTrack desde Loco"

    notCompatibleWithConfigurationCache(
        "Descarga archivos externos desde Loco"
    )

    doLast {
        val apiKey = locoApiKey.orNull
            ?.takeIf { it.isNotBlank() }
            ?: throw GradleException(
                "Falta LOCO_API_KEY en ~/.gradle/gradle.properties"
            )

        val workDir = layout.buildDirectory
            .dir("localise")
            .get()
            .asFile

        val zipFile = File(workDir, "translations.zip")
        val extracted = File(workDir, "extracted")
        val preview = File(workDir, "preview")

        val target = File(
            projectDir,
            "src/commonMain/composeResources"
        )

        workDir.mkdirs()
        extracted.deleteRecursively()
        extracted.mkdirs()
        preview.deleteRecursively()
        preview.mkdirs()

        val url = URI(
            "https://localise.biz/api/export/archive/xml.zip?format=android"
        ).toURL()

        val connection = url.openConnection()
                as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 30000

            connection.setRequestProperty(
                "Authorization",
                "Bearer $apiKey"
            )

            if (connection.responseCode != 200) {
                throw GradleException(
                    "Loco respondió HTTP ${connection.responseCode}"
                )
            }

            connection.inputStream.use { input ->
                zipFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } finally {
            connection.disconnect()
        }

        // Extraer el ZIP de forma segura
        ZipInputStream(zipFile.inputStream()).use { zip ->

            var entry = zip.nextEntry

            while (entry != null) {
                val output = File(extracted, entry.name)
                    .canonicalFile

                if (!output.toPath().startsWith(
                        extracted.canonicalFile.toPath()
                    )
                ) {
                    throw GradleException(
                        "Ruta ZIP no permitida"
                    )
                }

                if (entry.isDirectory) {
                    output.mkdirs()
                } else {
                    output.parentFile?.mkdirs()

                    output.outputStream().use { fileOutput ->
                        zip.copyTo(fileOutput)
                    }
                }

                zip.closeEntry()
                entry = zip.nextEntry
            }
        }

        // Localizar las carpetas de traducciones
        val valuesDirs = extracted.walkTopDown()
            .filter {
                it.isDirectory &&
                        it.name.matches(Regex("values(?:-.*)?")) &&
                        File(it, "strings.xml").isFile
            }
            .toList()

        if (valuesDirs.none { it.name == "values" }) {
            throw GradleException(
                "Loco no exportó values/strings.xml"
            )
        }

        val copiedNames = mutableSetOf<String>()

        valuesDirs.forEach { dir ->
            if (!copiedNames.add(dir.name)) {
                throw GradleException(
                    "Carpeta duplicada: ${dir.name}"
                )
            }

            val destination = File(
                preview,
                "${dir.name}/strings.xml"
            )

            destination.parentFile.mkdirs()

            File(dir, "strings.xml").copyTo(
                destination,
                overwrite = true
            )
        }

        // Comprobar que no desaparezcan claves existentes
        fun keys(file: File): Set<String> {
            val regex = Regex(
                """<string\s+name\s*=\s*["']([^"']+)["']"""
            )

            return regex.findAll(file.readText())
                .map { it.groupValues[1] }
                .toSet()
        }

        val existing = File(target, "values/strings.xml")
        val incoming = File(preview, "values/strings.xml")

        if (existing.exists()) {
            val missing = keys(existing) - keys(incoming)

            if (missing.isNotEmpty()) {
                throw GradleException(
                    "Faltan claves en Loco: ${missing.joinToString()}"
                )
            }
        }

        // Primera ejecución: solo preparar vista previa
        val applyChanges = project.findProperty("LOCO_APPLY")
            ?.toString() == "true"

        if (applyChanges) {
            copiedNames.forEach { folder ->
                val source = File(preview, "$folder/strings.xml")
                val destination = File(
                    target,
                    "$folder/strings.xml"
                )

                destination.parentFile.mkdirs()

                source.copyTo(
                    destination,
                    overwrite = true
                )

                println("Actualizado: $folder/strings.xml")
            }
        } else {
            println("Traducciones preparadas en:")
            println(preview.absolutePath)
            println("Revisa los archivos antes de aplicarlos.")
        }
    }
}

dependencies {
    "androidRuntimeClasspath"(libs.compose.uiTooling)

    add("kspAndroid", libs.androidx.room3.compiler)
    add("kspIosArm64", libs.androidx.room3.compiler)
    add(
        "kspIosSimulatorArm64",
        libs.androidx.room3.compiler
    )
}