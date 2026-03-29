package com.bratyslav.rescan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bratyslav.rescan.navigation.ReScanNavHost
import com.bratyslav.rescan.ui.theme.ReScanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReScanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ReScanNavHost()
                }
            }
        }
    }
}
