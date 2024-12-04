package com.c242ps263.riceup.disease.scanner

import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.disease.domain.usecase.disease.PredictDiseaseUseCase
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = hiltViewModel(),
    isPreview: Boolean = LocalInspectionMode.current
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = if(isPreview) {
        null
    } else {
        rememberPermissionState(android.Manifest.permission.CAMERA)
    }

    LaunchedEffect(true) {
        if (permissionState?.status?.isGranted == false) {
            permissionState.launchPermissionRequest()
        }
    }

    val preview = androidx.camera.core.Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    LaunchedEffect(lensFacing) {
        val cameraProvider = suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(context))
            }
        }
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview)
        preview.surfaceProvider = previewView.surfaceProvider
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            if ((isPreview || permissionState?.status?.isGranted == true) && viewModel.showScan) {
                AndroidView(
                    factory = {
                        previewView
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .background(color = Color.Black)
                        .combinedClickable(
                            onClick = {
                                if (permissionState == null) return@combinedClickable
                                when {
                                    permissionState.status.shouldShowRationale -> {
                                        Toast
                                            .makeText(
                                                context,
                                                "Izinkan penggunaan kamera",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }

                                    else -> {
                                        permissionState.launchPermissionRequest()
                                    }
                                }

                                viewModel.showScan = true
                            },
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(80.dp, 80.dp),
                            painter = painterResource(id = android.R.drawable.ic_menu_camera),
                            contentDescription = "QR Scan",
                            tint = MaterialTheme.colorScheme.background
                        )
                        Text(
                            "Izinkan penggunaan kamera",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .padding(top = 16.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = {

                    }
                ) {
                    Text("ANALYZE")
                }

                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = {

                    }
                ) {
                    Text("GALERI")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScannerPreview() {
    RiceUpTheme {
        ScannerScreen(
            viewModel = ScannerViewModel(null)
        )
    }
}