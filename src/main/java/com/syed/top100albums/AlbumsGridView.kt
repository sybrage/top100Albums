package com.syed.top100albums

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.syed.top100albums.ui.theme.AlbumDetailView


@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

fun AlbumsGridView(context: Context, albums: List<ResultsItem>?) {

    val albumsCount = albums?.size ?: 0
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray
                    ),
                title = {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        Text(text = context.getString(R.string.app_name))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { Toast.makeText(context, "Navigation Icon!!", Toast.LENGTH_LONG).show()}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    IconButton(onClick = { Toast.makeText(context, "Action Icon!", Toast.LENGTH_LONG).show()}) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                },
                elevation = 10.dp
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(albumsCount) {

                Box(
                    Modifier.fillMaxSize()
                ) {

                    AsyncImage(
                        model = albums?.get(it)?.artworkUrl100,
                        contentDescription = "Album Image",
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.itunesicon),
                        modifier = Modifier
                            .height(200.dp)
                            .clickable(onClick = {
                                Toast
                                    .makeText(context, "Image #${it + 1}", Toast.LENGTH_LONG)
                                    .show()
                                showAlbumDetails(context, albums?.get(it))
                            })
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Color.Black
                                    .copy(alpha = 0.3f)
                            )
                            .padding(horizontal = 5.dp),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            text = (it + 1).toString() + ". " +
                                    albums?.get(it)?.artistName + " - " +
                                    albums?.get(it)?.name,
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

fun showAlbumDetails(context: Context, albumData: ResultsItem?) {
    val intent = Intent(context, AlbumDetailView::class.java)
    intent.putExtra("albumData", albumData)
    context.startActivity(intent)
}