package com.bratyslav.rescan

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReScanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Ensures default app exists before Hilt injects FirebaseAuthApi / AuthRepository.
        // google-services also initializes Firebase; this is idempotent and fixes ordering edge cases.
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
    }
}
