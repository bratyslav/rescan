package com.bratyslav.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bratyslav.auth.AuthIntent
import com.bratyslav.auth.AuthMode
import com.bratyslav.auth.AuthUiState
import com.bratyslav.auth.AuthViewModel

@Composable
fun AuthScreen(
    onNavigateToScanner: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToScanner) {
        if (uiState.navigateToScanner) {
            onNavigateToScanner()
            viewModel.processIntent(AuthIntent.MarkNavigationAsConsumed)
        }
    }

    AuthScreenContent(
        state = uiState,
        onEvent = viewModel::processIntent,
    )
}

@Composable
internal fun AuthScreenContent(
    state: AuthUiState,
    onEvent: (AuthIntent) -> Unit,
    modifier: Modifier = Modifier.Companion,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
    ) {
        Text(
            text = "ReScan",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.Companion.height(24.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = state.mode == AuthMode.SignIn,
                onClick = { onEvent(AuthIntent.ModeSignIn) },
                label = { Text("Sign in") },
                modifier = Modifier.Companion.weight(1f),
                enabled = !state.isLoading,
            )
            FilterChip(
                selected = state.mode == AuthMode.SignUp,
                onClick = { onEvent(AuthIntent.ModeSignUp) },
                label = { Text("Sign up") },
                modifier = Modifier.Companion.weight(1f),
                enabled = !state.isLoading,
            )
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(AuthIntent.ChangeEmail(it)) },
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Email),
            enabled = !state.isLoading,
        )
        Spacer(modifier = Modifier.Companion.height(12.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = { onEvent(AuthIntent.ChangePassword(it)) },
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Password),
            enabled = !state.isLoading,
        )

        state.errorMessage?.let { message ->
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.Companion.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onEvent(AuthIntent.Submit) },
                modifier = Modifier.Companion.fillMaxWidth(),
            ) {
                val label = when (state.mode) {
                    AuthMode.SignIn -> "Sign in"
                    AuthMode.SignUp -> "Create account"
                }
                Text(label)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    MaterialTheme {
        AuthScreenContent(
            state = AuthUiState(email = "a@b.c", mode = AuthMode.SignIn),
            onEvent = {},
        )
    }
}