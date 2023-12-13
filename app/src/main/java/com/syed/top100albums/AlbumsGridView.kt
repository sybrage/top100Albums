package com.syed.top100albums

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.syed.top100albums.ui.theme.blackTrans
import com.syed.top100albums.ui.theme.whiteTrans

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun AlbumsGridView(context: Context, navController: NavHostController, albums: List<ResultsItem>) {
    if (albums.isEmpty()) navController.navigate("ConnectionView")
    val albumsCount = albums?.size ?: 0
    val gridState = rememberLazyGridState()
    val gridCellHeight = with(LocalDensity.current) { 80.dp.toPx() }
    val scrollPos =
        gridState.firstVisibleItemIndex * gridCellHeight + gridState.firstVisibleItemScrollOffset
    val density = LocalDensity.current
    val heightInDp = animateDpAsState(
        targetValue = if (scrollPos > (gridCellHeight / 2)) with(density) { 40.dp } else 80.dp,
        animationSpec = tween(
            durationMillis = 1000,
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(
                                align = if (scrollPos > (gridCellHeight / 2)) with(density) { Alignment.CenterHorizontally } else Alignment.Start),
                    ) {
                        Text(
                            text = context.getString(R.string.app_name),
                            fontSize = if (scrollPos > (gridCellHeight / 2)) with(density) { 15.sp } else 30.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                color = Color.Black
                            )
                        )
                    }
                },
                modifier = Modifier.height(heightInDp.value),
                backgroundColor = Color.White,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("GridView") {
                            popUpTo("ContentView") {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = null)
                    }
                },
            )
        }
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            items(albumsCount) {

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.White)
                        .fillMaxWidth(0.5F)
                ) {

                    AsyncImage(
                        model = albums?.get(it)?.artworkUrl100,
                        contentDescription = "Album Image",
                        contentScale = ContentScale.FillBounds,
                        error = painterResource(R.drawable.itunesicon),
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(onClick = {
                                showAlbumDetails(context, albums?.get(it))
                            })
                    )

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(blackTrans)
                            .padding(15.dp),
                        Arrangement.Bottom
                    ) {
                        Text(
                            text = if ((albums?.get(it)?.name ?: "").length > 40)
                                String.format("%.40s...", (albums?.get(it)?.name ?: "")) else
                                (albums?.get(it)?.name ?: ""),
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Start
                            )
                        )
                        Text(
                            text = if ((albums?.get(it)?.artistName ?: "").length > 60)
                                String.format(
                                    "%.60s...",
                                    (albums?.get(it)?.artistName ?: "")
                                ) else
                                (albums?.get(it)?.artistName ?: ""),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = whiteTrans
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