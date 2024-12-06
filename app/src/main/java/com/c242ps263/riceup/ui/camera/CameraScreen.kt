package com.c242ps263.riceup.ui.camera

import androidx.compose.runtime.Composable
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.ui.scanner.ScannerScreen

@Composable
fun CameraScreen(
    navigateToPredictionResult: (DetectionDisease) -> Unit = {}
) {
    ScannerScreen(
        navigateToPredictionResult = navigateToPredictionResult
    )
}