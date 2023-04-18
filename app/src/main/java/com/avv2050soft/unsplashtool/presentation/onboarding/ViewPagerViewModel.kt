package com.avv2050soft.unsplashtool.presentation.onboarding

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.avv2050soft.unsplashtool.domain.repository.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun getFragmentManager(): FragmentManager? {
        return fragmentManager
    }

    override fun onCleared() {
        super.onCleared()
        fragmentManager = null
    }

    fun saveIsOnboardingFinish(key: String, value: Boolean) {
        sharedPreferencesRepository.saveBoolean(key, value)
    }
}