package kz.grand_hotel.ui.menu.ui.profile

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

class LanguageManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    companion object {
        const val KEY_LANGUAGE = "app_language"
        const val DEFAULT_LANGUAGE = "en"
    }

    fun getCurrentLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

//    fun setNewLocale(activity: Activity, languageCode: String) {
//        persistLanguage(languageCode)
//        updateResources(activity, languageCode)
//        activity.recreate()
//    }

    private fun persistLanguage(languageCode: String) {
        editor.putString(KEY_LANGUAGE, languageCode)
        editor.apply()
    }

//    private fun updateResources(context: Context, languageCode: String) {
//        val locale = Locale(languageCode)
//        Locale.setDefault(locale)
//        val resources = context.resources
//        val config = Configuration(resources.configuration)
//
//        // Для Android 7.0 (API 24) и выше
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            config.setLocale(locale)
//            context.createConfigurationContext(config)  // Для API 24+
//        } else {
//            config.locale = locale
//            resources.updateConfiguration(config, resources.displayMetrics)
//        }
//
//        // Убедись, что ресурсы обновляются после смены языка
//        context.resources.updateConfiguration(config, context.resources.displayMetrics)
//    }


}