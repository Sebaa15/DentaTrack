package org.ucb.dentatrack.Login.presentation.Composable

import edu.ucb.ds.components.fields.MainTextField
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import edu.ucb.ds.components.buttons.MainButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import dentatrack.shared.generated.resources.Res
import dentatrack.shared.generated.resources.login_button
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginState

@Composable
fun LoginContent(
    state: LoginState,
    onRegisterClick: () -> Unit,
    onEvent: (LoginEvent) -> Unit,
    onSettings: () -> Unit
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

        MainTextField(
            value = state.email,
            onValueChange = {
                onEvent(LoginEvent.EmailChanged(it))
            },
            label = "Correo electrónico",
            keyboardType = KeyboardType.Email
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        MainTextField(
            value = state.password,
            onValueChange = {
                onEvent(LoginEvent.PasswordChanged(it))
            },
            label = "Contraseña",
            isPassword = true
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

        MainButton(
            text = stringResource(
                Res.string.login_button
            ),
            onClick = {
                onEvent(LoginEvent.LoginClicked)
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = onRegisterClick
        ) {
            Text("Crear cuenta")
        }
        TextButton(onClick = onSettings) {
            Text("Accesibilidad")
        }
    }
}