package org.ucb.dentatrack.Login.presentation.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginEvent
import androidx.compose.material3.TextButton
import org.ucb.dentatrack.login.presentation.Viewmodel.LoginState

@Composable
fun LoginContent(
    state: LoginState,
    onRegisterClick: () -> Unit,
    onEvent: (LoginEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "LOGIN",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "DentaTrack"
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { email ->
                onEvent(
                    LoginEvent.EmailChanged(email)
                )
            },
            label = {
                Text("Correo electrónico")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { password ->
                onEvent(
                    LoginEvent.PasswordChanged(password)
                )
            },
            label = {
                Text("Contraseña")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = state.error
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                onEvent(
                    LoginEvent.LoginClicked
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = onRegisterClick
        ) {
            Text("Crear cuenta")
        }
    }
}