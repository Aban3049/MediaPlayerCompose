package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun Pager(navHostController: NavHostController) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        HomeTabs.entries.size
    })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = { Text(text = currentTab.text) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTabIndex.value == index) currentTab.selectedIcon else currentTab.unSelectedIcon,
                            contentDescription = "TabIcons"
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (HomeTabs.entries[page]) {
                HomeTabs.Folder -> {
                    Folders(navHostController)
                }

                HomeTabs.Videos -> {
                    Videos(navHostController)
                }
            }
        }
    }
}

enum class HomeTabs(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val text: String
) {
    Folder(
        selectedIcon = Icons.AutoMirrored.Outlined.List,
        unSelectedIcon = Icons.AutoMirrored.Filled.List,
        text = "Folder"
    ),
    Videos(
        selectedIcon = Icons.Outlined.PlayArrow,
        unSelectedIcon = Icons.Filled.PlayArrow,
        text = "Videos"
    )
}


