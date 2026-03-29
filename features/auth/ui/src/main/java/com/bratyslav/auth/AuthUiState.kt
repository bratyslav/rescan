package com.bratyslav.auth

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val mode: AuthMode = AuthMode.SignIn,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToScanner: Boolean = false,
)
