package com.c242ps263.riceup.ui.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.c242ps263.core.data.UiState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

): ViewModel() {
    fun doSignOut(): Flow<UiState<Unit>> = callbackFlow {
        trySend(UiState.Loading)

        Firebase.auth.signOut()

        trySend(UiState.Success(Unit))

        awaitClose()
    }
}