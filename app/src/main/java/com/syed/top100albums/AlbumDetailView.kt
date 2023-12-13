package com.syed.top100albums

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.syed.top100albums.ui.theme.buttonBlue
import com.syed.top100albums.ui.theme.themeGray
import com.syed.top100albums.ui.theme.whiteTrans
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AlbumDetailView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val albumData: ResultsItem? = intent.extras?.getParcelable("albumData")
        val copyright = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
            .getString(getString(R.string.copyright), getString(R.string.copyrightDefault))

        setContent {
            window.statusBarColor = this.resources.getColor(R.color.whiteTrans)
            WindowCompat.getInsetsController(
                window,
                LocalView.current
            ).isAppearanceLightStatusBars = isSystemInDarkTheme().not()
            WindowCompat.setDecorFitsSystemWindows(window, false)

            Column(
                Modifier
                    .background(Color.White)
                    .fillMaxHeight()
            ) {
                Box() {
                    AsyncImage(
                        model = albumData?.artworkUrl100?.replace("100x100", "1400x1400"),
                        contentDescription = "Album Image",
                        error = painterResource(R.drawable.itunesicon),
                        modifier = Modifier
                            .layoutId("albumImage")
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .padding(start = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(whiteTrans)
                                .size(35.dp),
                        ) {
                            IconButton(
                                onClick = { finish() }) {
                                Icon(
                                    Icons.Rounded.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = albumData?.name ?: "",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = themeGray
                        ),
                        modifier = Modifier
                            .layoutId("albumName"),
                    )
                    Text(
                        text = albumData?.artistName ?: "",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .layoutId("albumArtistName"),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        albumData?.genres?.forEach() {
                            OutlinedButton(
                                onClick = {},
                                border = BorderStroke(1.dp, buttonBlue),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(10.dp, 2.dp),
                                modifier = Modifier
                                    .layoutId("albumGenre")
                                    .defaultMinSize(
                                        minWidth = 1.dp,
                                        minHeight = 1.dp
                                    )
                            ) {
                                Text(
                                    text = it?.name ?: "",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = Color.Blue
                                    )
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .layoutId("albumReleaseInfo")
                        .padding(top = 0.dp)
                        .padding(bottom = 35.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .layoutId("albumReleaseDate")
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = getString(R.string.release) + " " + releaseDate(albumData?.releaseDate ?: ""),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = themeGray
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .layoutId("copyrightNotice")
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = copyright.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = themeGray
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .layoutId("albumLinkButton")
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            enabled = isOnline(applicationContext),
                            onClick = {
                                val intent = Intent(applicationContext, AlbumPageView::class.java)
                                intent.putExtra("albumPageUrl", albumData?.url)
                                startActivity(intent)
                            },
                            shape = RoundedCornerShape(30),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = buttonBlue,
                                contentColor = Color.White,
                            )
                        ) {
                            Text(getString(R.string.album_link_button))
                        }
                    }
                }
            }
        }
    }

    fun releaseDate(date: String): String {
        return if (date.isNotEmpty())
            LocalDate.parse(date)
                .format(DateTimeFormatter
                    .ofPattern("MMM dd, uuuu", Locale.ENGLISH)) else date
    }
}