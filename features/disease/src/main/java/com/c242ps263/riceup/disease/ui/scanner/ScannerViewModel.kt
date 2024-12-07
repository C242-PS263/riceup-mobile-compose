package com.c242ps263.riceup.disease.ui.scanner

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps263.core.data.UiState
import com.c242ps263.riceup.disease.data.model.DetectionResponse
import com.c242ps263.riceup.disease.domain.usecase.disease.PredictDiseaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val predictDiseaseUseCase: PredictDiseaseUseCase?
): ViewModel() {
    var uiStatePrediction: MutableStateFlow<UiState<DetectionResponse>> = MutableStateFlow(UiState.Idle)
        private set

    var showScan by mutableStateOf(false)

    var imageCapture: ImageCapture? by mutableStateOf(null)
    var preview by mutableStateOf<Preview?>(null)
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val cameraProvider = cameraProviderFuture.get()

    fun captureImageFromCamera(context: Context, onMediaCaptured: (Uri) -> Unit) {
        val imgCapture = imageCapture ?: return

        val photoFile = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imgCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object: ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResult: ImageCapture.OutputFileResults) {
                    val uri = outputFileResult.savedUri
                    if (uri != null) {
                        onMediaCaptured(uri)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("ImageCapture", exception.message.toString())

                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_LONG).show()

                    uiStatePrediction.value = UiState.Idle
                }
            }
        )
    }

    fun predict(context: Context, uri: Uri, dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        context.contentResolver.openInputStream(uri)?.let {
            file.outputStream().use { output ->
                it.copyTo(output)
            }
            predict(file)
        }
    }

    fun predict(file: File, dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        uiStatePrediction.value = UiState.Loading

        viewModelScope.launch(dispatcher) {
            try {
                val result = predictDiseaseUseCase?.execute(
                    MultipartBody.Part
                        .createFormData("file",
                            file.name,
                            file.asRequestBody()
                        )
                )

                if (result != null) {
                    uiStatePrediction.value = UiState.Success(result.apply {
                        this.file = file.toString()
                    })
                } else {
                    uiStatePrediction.value = UiState.Error("Terjadi kesalahan")
                }
            } catch (e: Exception) {
                uiStatePrediction.value = UiState.Error(e.message.toString())
            }
        }
    }
}