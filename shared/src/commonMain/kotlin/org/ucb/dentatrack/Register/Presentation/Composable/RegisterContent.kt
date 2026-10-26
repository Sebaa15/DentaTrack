package org.ucb.dentatrack.Register.Presentation.Composable

import edu.ucb.ds.components.fields.MainTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.ucb.dentatrack.Register.Presentation.VIewmodel.RegisterState
import androidx.compose.material3.TextButton

@Composable
fun RegisterContent(
    state: RegisterState,
    onFullNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackToLogin: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "DentaTrack",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Crear cuenta"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        MainTextField(
            value = state.fullName,
            onValueChange = onFullNameChanged,
            label = "Nombre completo"
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MainTextField(
            value = state.email,
            onValueChange = onEmailChanged,
            label = "Correo electrónico",
            keyboardType = KeyboardType.Email
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MainTextField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = "Contraseña",
            isPassword = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MainTextField(
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            label = "Confirmar contraseña",
            isPassword = true
        )

        state.error?.let { error ->

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        MainButton(
            text = "Crear cuenta",
            onClick = onRegisterClick
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = onBackToLogin
        ) {
            Text("Ya tengo una cuenta")
        }
    }
}