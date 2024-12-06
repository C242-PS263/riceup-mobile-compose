package com.c242ps263.riceup.disease.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps263.core.data.UiState
import com.c242ps263.riceup.disease.data.datasource.local.db.entity.DetectionDiseaseEntity
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.domain.usecase.disease.db.GetDetectionDiseaseDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val getDetectionDiseaseDbUseCase: GetDetectionDiseaseDbUseCase
): ViewModel() {
    var uiState = MutableStateFlow<UiState<MutableList<DetectionDiseaseEntity>>>(UiState.Idle)
        private set

    fun getHistories(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        viewModelScope.launch(dispatcher) {
            uiState.value = UiState.Loading

            try {
                getDetectionDiseaseDbUseCase.execute(Unit).catch {
                    uiState.value = UiState.Error(it.message.toString())
                }.collect {
                    uiState.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}