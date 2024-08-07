package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.ui_layer.Navigation.Routes
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainHomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Video Player",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }, navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp)
                )
            }, modifier = Modifier.padding(start = 12.dp, end = 12.dp),colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    Image(
                        imageVector = Icons.Default.Widgets,
                        contentDescription = null,
                        modifier = Modifier.size(34.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                })
        }

    )
    { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF191722))
                .padding(innerPadding)
        ) {

            val (topItem) = createRefs()

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .constrainAs(topItem) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),
                        colors = CardDefaults.cardColors(Color(0xFF1f2130)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.time),
                                contentDescription = null,
                                modifier = Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Recent",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "121 Files",
                                color = Color(0xFF393b4a),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }

                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        colors = CardDefaults.cardColors(Color(0xFF1f2130)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.favorite),
                                contentDescription = null,
                                Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Favourite",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))


                            Text(
                                text = "30 Files",
                                color = Color(0xFF393b4a),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }


                    }

                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),
                        colors = CardDefaults.cardColors(Color(0xFF1f2130)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.music),
                                contentDescription = null,
                                modifier = Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Audio",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "20 Files",
                                color = Color(0xFF393b4a),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }

                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp)
                            .clickable {
                                navController.navigate(Routes.VideosScreen)
                            },
                        colors = CardDefaults.cardColors(Color(0xFF1f2130)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.video),
                                contentDescription = null,
                                Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Videos",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))


                            Text(
                                text = "80 Files",
                                color = Color(0xFF393b4a),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }


                    }

                }

                Text(
                    text = "Frequently Used",
                    color = Color(0xFF5c6477),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {

                    Card(
                        Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color(0xFF1f2130)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {


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
                                        text = "Camera",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "80 File",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp,
                                        color = Color(0xFF393b4a),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xFF5c5d6f)),
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
                                        text = "Download",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "20 File",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp,
                                        color = Color(0xFF393b4a),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xFF5c5d6f)),
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

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
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
                                        text = "Facebook",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "30 File",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp,
                                        color = Color(0xFF393b4a),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xFF5c5d6f)),
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
                                        text = "Inshot",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "10 File",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp,
                                        color = Color(0xFF393b4a),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xFF5c5d6f)),
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
                                        text = "Instagram",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "25 File",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp,
                                        color = Color(0xFF393b4a),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xFF5c5d6f)),
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
                                        text = "Snapchat",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "60 File",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp,
                                        color = Color(0xFF393b4a),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xFF5c5d6f)),
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

                }

            }

        }
    }


}

fun randomColor(): Color {
    val random = Random.nextInt(Colors.entries.size)
    return Colors.entries[random].color
}

enum class Colors(val color: Color) {
    COLOR1(Color(0xFFf8774f)),
    COLOR2(Color(0xFFfcd247)),
    COLOR3(Color(0xFF458df7)),
    COLOR4(Color(0xFF41e259)),
    COLOR5(Color(0xFF7f838f))
}