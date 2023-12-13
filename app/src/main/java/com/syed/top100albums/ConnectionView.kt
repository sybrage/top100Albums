package com.syed.top100albums

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.syed.top100albums.ui.theme.buttonBlue

@Composable
fun ConnectionView(context: Context, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(
            text = context.getString(R.string.noConnection),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
        Button(
            onClick = {
                navController.navigate("GridView") {
                    popUpTo("ContentView") {
                        inclusive = true
                    }
                }},
            modifier = Modifier.padding(30.dp),
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonBlue,
                contentColor = Color.White,
            )
        ) {
            Text(context.getString(R.string.reconnect))
        }
    }
}