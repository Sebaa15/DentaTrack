This is a Kotlin Multiplatform project targeting Android, iOS.

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

## Examen de Desarrollo Móvil — TP2

**Integrante:** Guery Sebastian Flores  
**Ejercicio seleccionado:** E100-4 — Sistema de notificaciones inteligente  
**Puntaje:** 100 puntos

### Tecnologías utilizadas

- Firebase Cloud Messaging (FCM)
- Firebase Remote Config
- Cloud Firestore
- Room
- WorkManager
- Android NotificationManager
- Kotlin Multiplatform y Compose

### Funcionalidad

La aplicación recibe notificaciones push, consulta las preferencias de Remote Config y registra las notificaciones en Room. Durante el horario silencioso, los mensajes procesados por el servicio se almacenan sin mostrarse. WorkManager revisa periódicamente los mensajes pendientes.

El usuario puede consultar el historial y abrir el detalle al seleccionar una notificación.

### Ejecución

1. Abrir DentaTrack en Android Studio.
2. Verificar la configuración de Firebase.
3. Ejecutar la aplicación en un dispositivo Android.
4. Iniciar sesión y verificar el token FCM en Firestore.
5. Enviar una notificación desde Firebase o mediante la API FCM.
6. Comprobar el historial y la navegación al detalle.

### Consideraciones

Para aplicar las preferencias de horario silencioso a mensajes recibidos en segundo plano, se utilizan mensajes FCM de tipo data-only. La ejecución periódica de WorkManager depende de las condiciones del sistema Android.