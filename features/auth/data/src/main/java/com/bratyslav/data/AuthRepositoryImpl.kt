package com.bratyslav.data

import com.bratyslav.data.firebase.FirebaseAuthApi
import com.bratyslav.domain.model.User
import com.bratyslav.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthApi: FirebaseAuthApi,
) : AuthRepository {

    override fun isUserLoggedIn(): Flow<Boolean> =
        firebaseAuthApi.authStateFlow().map { it != null }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): User =
        firebaseAuthApi.createUserWithEmailAndPassword(email, password)

    override suspend fun signInWithEmailAndPassword(email: String, password: String): User =
        firebaseAuthApi.signInWithEmailAndPassword(email, password)

    override suspend fun signOut() {
        firebaseAuthApi.signOut()
    }

    override suspend fun isLoggedIn(): Boolean = firebaseAuthApi.isLoggedIn()

    override suspend fun currentUser(): User? = firebaseAuthApi.currentUser()
}