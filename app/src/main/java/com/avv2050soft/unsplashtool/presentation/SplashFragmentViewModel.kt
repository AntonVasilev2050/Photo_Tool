package com.avv2050soft.unsplashtool.presentation

import androidx.lifecycle.ViewModel
import com.avv2050soft.unsplashtool.domain.repository.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    private var isOnboardingFinished = false

    fun getIsOnboardingFinished(key: String, defaultValue: Boolean): Boolean {
        isOnboardingFinished = sharedPreferencesRepository.getBoolean(key, defaultValue)
        return isOnboardingFinished
    }
}