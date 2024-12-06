package com.c242ps263.riceup.disease.ui.detection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.DetectionResponse

@Composable
fun DetectionScreen(
    detectionDisease: DetectionDisease,
    isPreview: Boolean = LocalInspectionMode.current,
    navigateToAdvice: (text: String) -> Unit = {}
) {
    Box (
       modifier = Modifier
           .fillMaxSize()
           .background(Color.White)
    ) {
        Column {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Column (
                modifier = Modifier
                    .padding(20.dp, 10.dp, 20.dp, 50.dp)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                ) {
                    Text(detectionDisease.prediction,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(detectionDisease.informationDescription,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp
                    )
                }
                Button (
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navigateToAdvice(detectionDisease.advice)
                    }
                ) {
                    Text("For more information")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetectionPreview() {
    RiceUpTheme {
        DetectionScreen(
            detectionDisease = DetectionDisease(
                prediction = "Prediction",
                advice = "lorem",
                informationDescription = "Description",
            ),
        )
    }
}