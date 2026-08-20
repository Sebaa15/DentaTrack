package org.ucb.dentatrack.presentation.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import dentatrack.shared.generated.resources.Res
import dentatrack.shared.generated.resources.*

@Composable
fun SigninPage() {

    var userSignIn by remember {
        mutableStateOf("")
    }

    var passwordSignIn by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign In"
        )

        TextField(
            value = userSignIn,
            onValueChange = {
                userSignIn = it
            },
            label = {
                Text(
                    text = stringResource(Res.string.signin_user)
                )
            },
            placeholder = {
                Text("Email Address")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = passwordSignIn,
            onValueChange = {
                passwordSignIn = it
            },
            label = {
                Text(
                    text = stringResource(Res.string.signin_password)
                )
            },
            placeholder = {
                Text("Password")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // TODO
            }
        ) {
            Text(
                text = stringResource(Res.string.signin_button)
            )
        }
    }
}