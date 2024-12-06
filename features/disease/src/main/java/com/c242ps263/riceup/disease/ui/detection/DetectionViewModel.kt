package com.c242ps263.riceup.disease.ui.detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps263.core.data.UiState
import com.c242ps263.riceup.disease.data.model.DetectionDisease
import com.c242ps263.riceup.disease.data.model.mapper.DiseaseMapper
import com.c242ps263.riceup.disease.domain.usecase.disease.GetDiseaseUseCase
import com.c242ps263.riceup.disease.domain.usecase.disease.db.InsertDetectionDiseaseDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class DetectionViewModel @Inject constructor(
    private val insertDetectionDiseaseDbUseCase: InsertDetectionDiseaseDbUseCase
): ViewModel() {
    var uiStateInsertHistory = MutableStateFlow<UiState<Any>>(UiState.Idle)
        private set

    @OptIn(ExperimentalUuidApi::class)
    fun insert(detectionDisease: DetectionDisease, dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        viewModelScope.launch(dispatcher) {
            uiStateInsertHistory.value = UiState.Loading

            try {
                val result = insertDetectionDiseaseDbUseCase.execute(DiseaseMapper.mapFromDetectionToEntity(
                    detectionDisease = detectionDisease,
                    id = Uuid.random().toString()
                ))
                if (result > 0) {
                    uiStateInsertHistory.value = UiState.Success(result)
                } else {
                    uiStateInsertHistory.value = UiState.Error("Gagal menyimpan data")
                }
            } catch (e: Exception) {
                uiStateInsertHistory.value = UiState.Error(e.message.toString())
            }
        }
    }
}