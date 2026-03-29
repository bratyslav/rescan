package com.bratyslav.auth

sealed interface AuthIntent {
    data class ChangeEmail(val value: String) : AuthIntent
    data class ChangePassword(val value: String) : AuthIntent
    data object ModeSignIn : AuthIntent
    data object ModeSignUp : AuthIntent
    data object Submit : AuthIntent
    data object MarkNavigationAsConsumed : AuthIntent
}
