package com.syed.top100albums.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.syed.top100albums.R
import com.syed.top100albums.ResultsItem

class AlbumDetailView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val albumData: ResultsItem? = intent.extras?.getParcelable("albumData")

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AsyncImage(
                        model = albumData?.artworkUrl100,
                        contentDescription = "Album Image",
                        contentScale = ContentScale.FillHeight,
                        error = painterResource(R.drawable.itunesicon),
                        modifier = Modifier.matchParentSize()
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .align(Alignment.Center)
                            .background(
                                Color.Black
                                    .copy(alpha = 0.5f)
                            )
                            .padding(horizontal = 5.dp),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {finish()}) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = albumData?.artistName + " - " + albumData?.name,
                            style = TextStyle(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}