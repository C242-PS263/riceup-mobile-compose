package com.c242ps263.riceup.disease.ui.home

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps263.core.data.UiState
import com.c242ps263.riceup.disease.data.model.Disease
import com.c242ps263.riceup.disease.domain.usecase.disease.GetDiseaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getDiseaseUseCase: GetDiseaseUseCase
): ViewModel() {
    var uiStateGetDisease: MutableStateFlow<UiState<List<Disease>>> = MutableStateFlow(UiState.Idle)
        private set

    val isLoading: Boolean
        get() = uiStateGetDisease.value is UiState.Loading

    fun getDiseases(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        viewModelScope.launch(dispatcher) {
            uiStateGetDisease.value = UiState.Loading
            try {
                getDiseaseUseCase.execute(Unit).catch {
                    uiStateGetDisease.value = UiState.Error(it.message.toString())
                }.collectIndexed { id, diseases ->
                    uiStateGetDisease.value = UiState.Success(diseases.filterNotNull())
                }
            } catch (e: Exception) {
                Log.e("HomeScreen", e.stackTraceToString())
                uiStateGetDisease.value = UiState.Error(e.message.toString())
            }
        }
    }
}