package com.bratyslav.auth

import com.google.firebase.auth.FirebaseAuthException

/**
 * Maps Firebase Auth failures to actionable UI text.
 * [CONFIGURATION_NOT_FOUND] almost always means Email/Password is off in Firebase Console.
 */
internal fun Throwable.toAuthErrorMessage(): String {
    val firebase = findFirebaseAuthException() ?: return message ?: "Something went wrong"

    return when {
        firebase.errorCode == "ERROR_CONFIGURATION_NOT_FOUND" ||
            firebase.message?.contains("CONFIGURATION_NOT_FOUND", ignoreCase = true) == true ->
            "Sign-in isn’t configured: open Firebase Console → Authentication → Sign-in method, " +
                "enable Email/Password, then try again."

        firebase.errorCode == "ERROR_OPERATION_NOT_ALLOWED" ->
            "This sign-in method is disabled for this app (Firebase Console → Authentication)."

        firebase.errorCode == "ERROR_INVALID_EMAIL" -> "That email address doesn’t look valid."

        firebase.errorCode == "ERROR_WRONG_PASSWORD" ||
            firebase.errorCode == "ERROR_USER_NOT_FOUND" ->
            "Wrong email or password."

        firebase.errorCode == "ERROR_EMAIL_ALREADY_IN_USE" ->
            "An account already exists for this email. Try Sign in."

        firebase.errorCode == "ERROR_WEAK_PASSWORD" ->
            "Password is too weak. Use at least 6 characters."

        else -> firebase.message ?: firebase.errorCode ?: "Something went wrong"
    }
}

private fun Throwable.findFirebaseAuthException(): FirebaseAuthException? {
    generateSequence(this) { it.cause }.forEach { t ->
        if (t is FirebaseAuthException) return t
    }
    return null
}
