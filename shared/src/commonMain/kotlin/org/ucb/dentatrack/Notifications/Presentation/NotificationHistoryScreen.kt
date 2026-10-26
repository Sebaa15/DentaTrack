package org.ucb.dentatrack.Notifications.Presentation


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.ucb.dentatrack.Notifications.Data.Dao.NotificationDao

@Composable
fun NotificationHistoryScreen(
    onOpenDetail: (String) -> Unit
) {
    val dao = koinInject<NotificationDao>()
    val flow = remember(dao) { dao.observeAll() }
    val history by flow.collectAsState(initial = emptyList())

    Column(
        Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Historial de notificaciones",
            style = MaterialTheme.typography.headlineSmall
        )

        history.forEach { item ->
            TextButton(
                onClick = { onOpenDetail(item.messageId) }
            ) {
                Text(
                    "${item.title} - " +
                            if (item.displayed) "Mostrada" else "Silenciada"
                )
            }
        }
    }
}