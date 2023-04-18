package com.avv2050soft.unsplashtool.data.repository

import android.content.Context
import com.avv2050soft.unsplashtool.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

private const val SPLASH_TOOL_PREFERENCES = "splash_tool_preferences"

class SharedPreferencesRepositoryImpl @Inject constructor(
    context: Context
) : SharedPreferencesRepository{

    private val sharedPreferences =
        context.getSharedPreferences(SPLASH_TOOL_PREFERENCES, Context.MODE_PRIVATE)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
}