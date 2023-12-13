package com.syed.top100albums

import android.R
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())
        //Realm.deleteRealm(RealmConfiguration.create(schema = setOf(AlbumData::class)))
        setContent {
            val window = this.window
            window.statusBarColor = this.resources.getColor(R.color.white)
            WindowCompat.getInsetsController(
                window,
                LocalView.current
            ).isAppearanceLightStatusBars = isSystemInDarkTheme().not()
            val navController = rememberNavController()
            Nav(this, navController, "GridView")
        }
    }
}