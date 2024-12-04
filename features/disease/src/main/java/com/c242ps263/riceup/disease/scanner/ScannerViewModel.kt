package com.c242ps263.riceup.disease.scanner

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps263.core.data.UiState
import com.c242ps263.riceup.disease.data.model.PredictResponse
import com.c242ps263.riceup.disease.domain.usecase.disease.PredictDiseaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val predictDiseaseUseCase: PredictDiseaseUseCase?
): ViewModel() {
    var uiState: UiState<PredictResponse> by mutableStateOf(UiState.Loading)
        private set

    var showScan by mutableStateOf(false)

    fun predict(file: File, dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        viewModelScope.launch(dispatcher) {
            try {
                predictDiseaseUseCase?.execute(
                    MultipartBody.Part
                        .createFormData(
                            "file",
                            file.name,
                            file.asRequestBody()
                        )
                )
            } catch (e: Exception) {
                uiState = UiState.Error(e.message.toString())
            }
        }
    }
}