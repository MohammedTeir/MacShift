// File: PreferencesHelper.kt 
package com.macshift.utils

import android.content.Context 
import android.content.SharedPreferences 
import com.macshift.App

object PreferencesHelper { private const val PREFS_NAME = "macshift_prefs" private const val KEY_ORIGINAL_MAC = "original_mac" private const val KEY_AUTO_SPOOF = "auto_spoof" private const val KEY_PROFILE_PREFIX = "profile_"

private val prefs: SharedPreferences
    get() = App.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

fun saveOriginalMac(mac: String) {
    prefs.edit().putString(KEY_ORIGINAL_MAC, mac).apply()
}

fun getOriginalMac(): String? {
    return prefs.getString(KEY_ORIGINAL_MAC, null)
}

fun setAutoSpoofOnBoot(enabled: Boolean) {
    prefs.edit().putBoolean(KEY_AUTO_SPOOF, enabled).apply()
}

fun isAutoSpoofOnBoot(): Boolean {
    return prefs.getBoolean(KEY_AUTO_SPOOF, false)
}

fun saveProfile(name: String, mac: String) {
    prefs.edit().putString(KEY_PROFILE_PREFIX + name, mac).apply()
}

fun getProfile(name: String): String? {
    return prefs.getString(KEY_PROFILE_PREFIX + name, null)
}

fun getAllProfiles(): Map<String, String> {
    return prefs.all

n            .filterKeys { it.startsWith(KEY_PROFILE_PREFIX) } .mapKeys { it.key.removePrefix(KEY_PROFILE_PREFIX) } .mapValues { it.value as String } }

fun deleteProfile(name: String) {
    prefs.edit().remove(KEY_PROFILE_PREFIX + name).apply()
}

}

