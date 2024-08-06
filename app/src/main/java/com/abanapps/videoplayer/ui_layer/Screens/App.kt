package com.abanapps.videoplayer.ui_layer.Screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import com.abanapps.videoplayer.ui_layer.Navigation.AppNavigation
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App(modifier: Modifier = Modifier, viewModel: PlayerViewModel = hiltViewModel()) {

    val context = LocalContext.current

    lateinit var mediaPermissionState: PermissionState

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        mediaPermissionState =
            rememberPermissionState(permission = Manifest.permission.READ_MEDIA_VIDEO)
    }
    val mediaPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            viewModel.showUi.value = it
        }

    val permissionLauncher2 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.showUi.value = true
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val writeSettingsPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(context)) {
                    // Permission granted
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission Required for App to function properly", Toast.LENGTH_SHORT).show()
                }
            }
        }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!mediaPermissionState.status.isGranted) {
                mediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
            } else {
                viewModel.showUi.value = true
            }
        } else {
            if (checkPermissions(context)) {
                viewModel.showUi.value = true
            } else {
                permissionLauncher2.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        checkAndRequestWriteSettingsPermission(context,writeSettingsPermissionLauncher)
    }

    val state = viewModel.showUi.collectAsState()

    if (state.value) {
        AppNavigation()
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Permission Not Granted")
            Button(onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                } else {
                    permissionLauncher2.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }, shape = RoundedCornerShape(10.dp)) {
                Text(text = "Grant Permission")
            }
        }
    }

}

fun checkAndRequestWriteSettingsPermission(
    context: Context,
    launcher: ActivityResultLauncher<Intent>
) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_SETTINGS
        ) != PermissionChecker.PERMISSION_GRANTED
    ) {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        } else {
            Intent(Settings.ACTION_SETTINGS)
        }
        intent.data = Uri.parse("package:" + context.packageName)
        launcher.launch(intent)
    }
}


private fun checkPermissions(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_AUDIO
        ) == PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_VIDEO
                ) == PermissionChecker.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PermissionChecker.PERMISSION_GRANTED
    }
}







