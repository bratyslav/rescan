package com.bratyslav.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bratyslav.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun processIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.ChangeEmail ->
                _state.update { it.copy(email = intent.value, errorMessage = null) }
            is AuthIntent.ChangePassword ->
                _state.update { it.copy(password = intent.value, errorMessage = null) }
            AuthIntent.ModeSignIn ->
                _state.update { it.copy(mode = AuthMode.SignIn, errorMessage = null) }
            AuthIntent.ModeSignUp ->
                _state.update { it.copy(mode = AuthMode.SignUp, errorMessage = null) }
            AuthIntent.Submit ->
                submit()
            AuthIntent.MarkNavigationAsConsumed ->
                _state.update { it.copy(navigateToScanner = false) }
        }
    }

    private fun submit() {
        val snapshot = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                when (snapshot.mode) {
                    AuthMode.SignIn ->
                        authRepository.signInWithEmailAndPassword(snapshot.email, snapshot.password)
                    AuthMode.SignUp ->
                        authRepository.createUserWithEmailAndPassword(snapshot.email, snapshot.password)
                }
                _state.update { it.copy(isLoading = false, navigateToScanner = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.toAuthErrorMessage(),
                    )
                }
            }
        }
    }
}
