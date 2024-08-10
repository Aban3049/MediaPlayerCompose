package com.abanapps.videoplayer.ui_layer.Utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.ui_layer.Screens.randomColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title:String,navHostController: NavHostController){

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "backBtn",
                tint = Color.White,
                modifier = Modifier.clickable {
                    navHostController.popBackStack()
                }
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "moreBtn",
                tint = Color.White,
                modifier = Modifier.clickable {

                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 2.dp)
            .height(50.dp)
    )

}

@Composable
fun FileItem(path:String="Camera",files:String="80 Files"){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.file),
                contentDescription = null,
                colorFilter = ColorFilter.tint(randomColor()),
                modifier = Modifier.size(34.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = path,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontSize = 16.sp
                )

                Text(
                    text = files,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = Color(0xFFFF99989d),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black)
    )
}

//0xFF393b4a files color