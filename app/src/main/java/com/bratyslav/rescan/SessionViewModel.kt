package com.bratyslav.rescan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bratyslav.domain.usecase.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SessionState {
    data object Loading : SessionState
    data object SignedIn : SessionState
    data object SignedOut : SessionState
}

@HiltViewModel
class SessionViewModel @Inject constructor(
    getAuthState: GetAuthStateUseCase,
) : ViewModel() {

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    init {
        viewModelScope.launch {
            getAuthState().collect { loggedIn ->
                _sessionState.value =
                    if (loggedIn) SessionState.SignedIn else SessionState.SignedOut
            }
        }
    }
}
