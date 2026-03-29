package com.bratyslav.domain.repository

import com.bratyslav.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun createUserWithEmailAndPassword(email: String, password: String): User
    suspend fun signInWithEmailAndPassword(email: String, password: String): User
    suspend fun signOut()
    suspend fun isLoggedIn(): Boolean
    suspend fun currentUser(): User?
}
