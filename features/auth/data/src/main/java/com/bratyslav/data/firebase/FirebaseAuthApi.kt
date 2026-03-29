package com.bratyslav.data.firebase

import android.util.Log
import com.bratyslav.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Singleton
class FirebaseAuthApi @Inject constructor() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun authStateFlow(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            Log.d("Debugging Log", "${firebaseAuth.currentUser?.toDomainUser()}")
            trySend(firebaseAuth.currentUser?.toDomainUser())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String): User {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.toDomainUser()
            ?: error("Firebase user missing after sign-up")
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): User {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.toDomainUser()
            ?: error("Firebase user missing after sign-in")
    }

    suspend fun signOut() {
        auth.signOut()
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun currentUser(): User? = auth.currentUser?.toDomainUser()
}

private fun FirebaseUser.toDomainUser() = User(id = uid)
