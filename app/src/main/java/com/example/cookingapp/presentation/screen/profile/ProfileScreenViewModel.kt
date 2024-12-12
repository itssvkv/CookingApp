package com.example.cookingapp.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.R
import com.example.cookingapp.data.local.datastore.DataStoreRepository
import com.example.cookingapp.data.remote.firebase.FirebaseRepository
import com.example.cookingapp.model.AboutItem
import com.example.cookingapp.utils.Constants.IS_LOGGED_IN
import com.example.cookingapp.utils.onResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserInfo()
            generateAboutItems()
        }
    }

    private suspend fun getUserInfo() {
        firebaseRepository.getTheUserInfo().onResponse(
            onLoading = {},
            onSuccess = { user ->
                user?.let { theUser ->
                    _uiState.update {
                        it.copy(
                            userPhoto = theUser.photoUrl,
                            userName = theUser.displayName,
                            userEmail = theUser.email,
                            userId = theUser.uid
                        )
                    }
                }
            },
            onFailure = {}
        )
    }

    fun updateIsShowBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(isShowBottomSheet = value)
        }

    }

    private fun generateAboutItems() {
        _uiState.update {
            it.copy(
                aboutItems = listOf(
                    AboutItem(
                        icon = R.drawable.facebook_icon,
                        url = "https://m.facebook.com/itssvkv/"
                    ),
                    AboutItem(
                        icon = R.drawable.twitter_icon,
                        url = "https://x.com/itssvkv"
                    ),
                    AboutItem(
                        icon = R.drawable.github_icon,
                        url = "https://github.com/itssvkv"
                    ),
                    AboutItem(
                        icon = R.drawable.linkedin_icon,
                        url = "https://www.linkedin.com/in/moh4medgamal/"
                    ),
                    AboutItem(
                        icon = R.drawable.whatsapp_icon,
                        url = "https://wa.me/+201207043786?text=Hello Mohamed i hope you doing well"
                    ),
                    AboutItem(
                        icon = R.drawable.telegram_icon,
                        url = "tg://msg?to=+201207043786?text=Hello Mohamed i hope you doing well"
                    )
                )
            )
        }
    }

    fun updateIsShowAlertDialog(value: Boolean) {
        _uiState.update { it.copy(isOpenAlertDialog = value) }
    }

    fun isLogoutSuccessful() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveToDataStore(key = IS_LOGGED_IN, value = false)

        }
    }
}
