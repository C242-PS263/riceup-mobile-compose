package com.c242ps263.riceup.disease.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.c242ps263.core.data.UiState
import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import java.io.File

@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel = hiltViewModel()
) {
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text("Terbaru",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(Modifier.height(20.dp))
        viewModel.uiState.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Idle -> {
                    viewModel.getHistories()
                }
                is UiState.Success -> {
                    Column (
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        uiState.data.forEach {
                            HistoryItem(detectionDisease = it)
                        }
                    }
                }
                else -> {

                }
            }
        }
    }
}

@Composable
fun HistoryItem(detectionDisease: DetectionDiseaseEntity) {
    val context = LocalContext.current
    Card (
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ) {
        Row (
            modifier = Modifier
                .padding(10.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(File(detectionDisease.file.toString()))
                    .build(),
                contentDescription = detectionDisease.prediction,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column (
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                Text(detectionDisease.prediction,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}