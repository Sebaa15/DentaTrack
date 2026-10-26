package org.ucb.dentatrack

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private var notificationMessageId by
    mutableStateOf<String?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(
                Manifest.permission
                    .POST_NOTIFICATIONS
            ) !=
            PackageManager
                .PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(
                    Manifest.permission
                        .POST_NOTIFICATIONS
                ),
                100
            )
        }
        notificationMessageId = intent?.getStringExtra("message_id")
        setContent {
            App(notificationMessageId = notificationMessageId)
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        notificationMessageId =
            intent.getStringExtra("message_id")
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}