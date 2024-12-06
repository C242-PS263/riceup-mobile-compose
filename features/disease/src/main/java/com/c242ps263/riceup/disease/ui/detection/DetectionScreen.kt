package com.c242ps263.riceup.disease.ui.detection

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.c242ps263.core.data.UiState
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.data.model.mapper.DiseaseMapper
import java.io.File

@Composable
fun DetectionScreen(
    detectionDisease: DetectionDisease,
    viewModel: DetectionViewModel = hiltViewModel(),
    isPreview: Boolean = LocalInspectionMode.current,
    navigateToAdvice: (text: String) -> Unit = {},
    navigateToHistory: () -> Unit = {}
) {
    val context = LocalContext.current
    Box (
       modifier = Modifier
           .fillMaxSize()
           .background(Color.White)
    ) {
        viewModel.uiStateInsertHistory.collectAsState(initial = UiState.Idle).value.let { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    navigateToHistory()

                    viewModel.uiStateInsertHistory.value = UiState.Idle
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
                is UiState.Error -> {
                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }

        Column (
            modifier = Modifier
                .padding(10.dp)
        ) {
            Box {
                if (detectionDisease.file != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(File(detectionDisease.file.toString()))
                            .build(),
                        contentDescription = detectionDisease.prediction,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(
                            (-30).dp,
                            22.dp
                        ),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    onClick = {
                        viewModel.insert(detectionDisease)
                    }
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_save),
                        contentDescription = "Save",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
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
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text("Deskripsi",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(detectionDisease.informationDescription,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
                Button (
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navigateToAdvice(detectionDisease.advice)
                    }
                ) {
                    Text("LIHAT SARAN DARI KAMI ( AI )")
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