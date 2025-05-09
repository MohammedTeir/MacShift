// File: MacRepository.kt 
package com.macshift.repository

import com.macshift.utils.ShellUtils 
import com.macshift.utils.MacUtils 
import com.macshift.utils.PreferencesHelper

class MacRepository { private val prefs = PreferencesHelper()

/**
 * Checks if device has root access
 */
fun checkRoot(): Boolean {
    return ShellUtils.hasRoot()
}

/**
 * Returns the current MAC address of wlan0 interface
 */
fun getCurrentMac(): String {
    return MacUtils.getCurrentMac()
}

/**
 * Generates a random MAC, applies it, and returns the new MAC
 */
fun spoofRandomMac(): String {
    val newMac = MacUtils.generateRandomMac()
    applyMac(newMac)
    return newMac
}

/**
 * Validates custom MAC, applies it
 */
fun spoofCustomMac(mac: String) {
    if (!MacUtils.isValidMac(mac)) {
        throw IllegalArgumentException("Invalid MAC format: $mac")
    }
    applyMac(mac)
}

/**
 * Restores the original MAC saved at first spoof
 */
fun restoreOriginalMac() {
    val original = prefs.getOriginalMac()
        ?: throw IllegalStateException("No original MAC stored to restore.")
    applyMac(original)
}

/**
 * Saves the auto-spoof-on-boot setting
 */
fun setAutoSpoofOnBoot(enabled: Boolean) {
    prefs.setAutoSpoofOnBoot(enabled)
}

/**
 * Internal helper: saves original once and applies the desired MAC
 */
private fun applyMac(mac: String) {
    // Save original MAC if not already saved
    if (prefs.getOriginalMac() == null) {
        prefs.saveOriginalMac(MacUtils.getCurrentMac())
    }
    // Apply the MAC via MacUtils
    MacUtils.applyMacAddress(mac)
}

}

