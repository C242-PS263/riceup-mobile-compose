package com.c242ps263.riceup.disease.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.c242ps263.core.data.UiState
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.disease.ui.home.HomeScreenViewModel
import com.c242ps263.riceup.disease.ui.home.section.DiseaseItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = viewModel.isLoading,
            onRefresh = {
                viewModel.getDiseases()
            }
        )

        Column(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .verticalScroll(rememberScrollState())
                .padding(15.dp)
        ) {
            Column {
                Text(
                    "Pindai Masalah,",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Companion.Bold
                    )
                )
                Text(
                    "Temukan Solusi!",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Companion.Bold
                    )
                )
            }
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp)
                ) {
                    Column {
                        Text(
                            "Bantu Temukan Solusi",
                            style = TextStyle(
                                color = Color.Companion.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Companion.Bold
                            )
                        )
                        Text(
                            "untuk Masalah",
                            style = TextStyle(
                                color = Color.Companion.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Companion.Bold
                            )
                        )
                        Text(
                            "Padi Anda!",
                            style = TextStyle(
                                color = Color.Companion.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Companion.Bold
                            )
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                "Jenis Penyakit",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Companion.Bold
                )
            )

            Spacer(Modifier.height(20.dp))

            viewModel.uiStateGetDisease.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Idle -> {
                        viewModel.getDiseases()
                    }
                    is UiState.Success -> {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            uiState.data.forEach {
                                DiseaseItem(
                                    disease = it,
                                )
                            }
                        }
                    }
                    is UiState.Loading -> {
                        Text("Loading")
                    }
                    is UiState.Error -> {
                        Text(uiState.errorMessage)
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = viewModel.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    RiceUpTheme {
        Surface {
            HomeScreen()
        }
    }
}