package com.example.shacklehotelbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.shacklehotelbuddy.search.ui.MainScreen
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShackleHotelBuddyTheme {
               MainScreen()
            }
        }
    }
}