package com.bratyslav.data

import com.bratyslav.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(): AuthRepository {
    override fun isUserLoggedIn(): Flow<Boolean> = flow { emit(true) }
}