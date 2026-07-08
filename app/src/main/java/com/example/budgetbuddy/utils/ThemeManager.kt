package com.example.budgetbuddy.utils

import android.content.Context

class ThemeManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("theme_pref", Context.MODE_PRIVATE)

    fun saveDarkMode(isDark: Boolean) {
        prefs.edit().putBoolean("dark_mode", isDark).apply()
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean("dark_mode", false)
    }
}