package com.c242ps263.riceup.auth.ui.signin

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps263.core.data.UiState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@HiltViewModel
class SignInViewModel @Inject constructor(
): ViewModel() {
    var uiSignState: MutableStateFlow<UiState<AuthResult>> = MutableStateFlow(UiState.Idle)
        private set

    fun doGoogleSignIn(
        context: Context,
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {
        uiSignState.value = UiState.Loading

        val getGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setServerClientId(context.resources.getString(com.c242ps263.riceup.auth.R.string.web_client_id))
            .setNonce(createNonce())
            .build()

        val getCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()

        viewModelScope.launch(dispatcher) {
            try {
                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(
                    request = getCredentialRequest,
                    context = context
                )

                val credential = result.credential

                when (credential) {
                    is CustomCredential -> {
                        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            try {
                                val googleIdTokenCredential = GoogleIdTokenCredential
                                    .createFrom(credential.data)

                                val authCredential = GoogleAuthProvider.getCredential(
                                    googleIdTokenCredential.idToken,
                                    null
                                )

                                Firebase.auth.signInWithCredential(authCredential)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            uiSignState.value = UiState.Success(it.result)
                                        } else {
                                            uiSignState.value = UiState.Error(it.exception?.message.toString())
                                        }
                                    }
                                    .addOnFailureListener {
                                        uiSignState.value = UiState.Error(it.message.toString())
                                    }
                                    .addOnCanceledListener {
                                        uiSignState.value = UiState.Error("Operation cancelled")
                                    }
                            } catch (e: Exception) {
                                uiSignState.value = UiState.Error(e.message.toString())
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                uiSignState.value = UiState.Error(e.message.toString())
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createNonce(): String {
        val rawNonce = Uuid.random().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it ->
            str + "%02x".format(it)
        }

        return hashedNonce
    }
}