package com.c242ps263.riceup.disease.ui.scanner

import android.Manifest
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.c242ps263.core.data.UiState
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.data.model.mapper.DiseaseMapper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = hiltViewModel(),
    isPreview: Boolean = LocalInspectionMode.current,
    navigateToPredictionResult: (DetectionDisease) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = if(isPreview) {
        null
    } else {
        rememberPermissionState(Manifest.permission.CAMERA)
    }

    LaunchedEffect(true) {
        if (permissionState?.status?.isGranted == false) {
            permissionState.launchPermissionRequest()
        }
    }

    Box (
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        viewModel.uiStatePrediction.collectAsState(initial = UiState.Idle).value.let { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    navigateToPredictionResult(DiseaseMapper.mapFromResponseToModel(uiState.data))

                    viewModel.uiStatePrediction.value = UiState.Idle
                }

                is UiState.Loading -> {
                    Dialog(
                        onDismissRequest = { },
                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    ) {
                        Box(
                            contentAlignment= Alignment.Center,
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
//                is UiState.Error -> TODO()
//                is UiState.Idle -> TODO()
                else -> {

                }
            }
        }

        Column {
            if ((isPreview || permissionState?.status?.isGranted == true) && viewModel.showScan) {
                AndroidView(
                    factory = {
                        val previewView = PreviewView(it)
                        viewModel.cameraProviderFuture.addListener({
                            viewModel.imageCapture = ImageCapture.Builder()
                                .setTargetRotation(previewView.display.rotation)
                                .build()

                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()

                            viewModel.cameraProvider.unbindAll()
                            viewModel.cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                viewModel.imageCapture,
                                viewModel.preview
                            )
                        }, ContextCompat.getMainExecutor(context))
                        viewModel.preview = androidx.camera.core.Preview.Builder()
                            .build()
                            .apply {
                                surfaceProvider = previewView.surfaceProvider
                            }

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
                        .weight(1.0f)
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
                        viewModel.captureImageFromCamera(context) {
                            viewModel.predict(it.toFile())
                        }
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
    val context = LocalContext.current

    RiceUpTheme {
        ScannerScreen(
            viewModel = ScannerViewModel(
                context,
                null
            )
        )
    }
}