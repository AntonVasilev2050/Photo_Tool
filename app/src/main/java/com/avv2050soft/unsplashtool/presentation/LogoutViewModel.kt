package com.avv2050soft.unsplashtool.presentation

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.unsplashtool.data.auth.AuthRepository
import com.avv2050soft.unsplashtool.data.repository.DatabaseRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationService

class LogoutViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())
    private val databaseRepository = DatabaseRepositoryImpl(application)

    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val logoutPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val logoutCompletedEventChannel = Channel<Unit>(Channel.BUFFERED)

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val logoutPageFlow: Flow<Intent>
        get() = logoutPageEventChannel.receiveAsFlow()

    val logoutCompletedFlow: Flow<Unit>
        get() = logoutCompletedEventChannel.receiveAsFlow()

    fun logout() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val logoutPageIntent = authService.getEndSessionRequestIntent(
            authRepository.getEndSessionRequest(),
            customTabsIntent
        )
        viewModelScope.launch {
            databaseRepository.deleteAllPhotosFromDb()
        }
        logoutPageEventChannel.trySendBlocking(logoutPageIntent)
    }

    fun webLogoutComplete() {
        authRepository.logout()
        logoutCompletedEventChannel.trySendBlocking(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}