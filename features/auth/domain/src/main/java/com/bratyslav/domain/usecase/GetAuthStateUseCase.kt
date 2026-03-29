package com.bratyslav.domain.usecase

import com.bratyslav.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.isUserLoggedIn()
}