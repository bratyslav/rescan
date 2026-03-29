package com.bratyslav.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserLoggedIn(): Flow<Boolean>
}