package org.ucb.dentatrack.Notifications.Presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.ucb.dentatrack.Notifications.Data.Dao.NotificationDao

@Composable
fun NotificationDetailScreen(
    messageId: String,
    onBack: () -> Unit
) {
    val dao = koinInject<NotificationDao>()
    val flow = remember(dao, messageId) {
        dao.observeById(messageId)
    }
    val notification by flow.collectAsState(initial = null)

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            notification?.title ?: "Notificación no encontrada",
            style = MaterialTheme.typography.headlineSmall
        )

        notification?.let {
            Text(it.body)
            Text("ID: ${it.messageId}")
            Text(if (it.displayed) "Mostrada" else "Pendiente")
        }

        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}