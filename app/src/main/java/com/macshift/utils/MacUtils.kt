// File: MacUtils.kt 
package com.macshift.utils

import kotlin.random.Random

object MacUtils { private const val INTERFACE = "wlan0"

/**
 * Retrieves the current MAC address of the Wi-Fi interface
 */
fun getCurrentMac(): String {
    // Try reading from sysfs
    return try {
        ShellUtils.execRoot("cat /sys/class/net/$INTERFACE/address")
    } catch (_: Exception) {
        // Fallback to ip link
        val output = ShellUtils.execRoot("ip link show $INTERFACE")
        // parse "link/ether XX:XX:XX:XX:XX:XX"
        Regex("link/ether ([0-9A-Fa-f:]{17})").find(output)
            ?.groups?.get(1)?.value
            ?: throw RuntimeException("Unable to parse MAC from ip link output")
    }
}

/**
 * Generates a random unicast MAC address
 */
fun generateRandomMac(): String {
    val bytes = ByteArray(6).apply {
        Random.nextBytes(this)
        // Clear multicast bit to ensure unicast MAC
        this[0] = (this[0].toInt() and 0xFE).toByte()
    }
    return bytes.joinToString(":") { String.format("%02X", it) }
}

/**
 * Validates MAC address format XX:XX:XX:XX:XX:XX
 */
fun isValidMac(mac: String): Boolean {
    return Regex("^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}\$").matches(mac)
}

/**
 * Applies the given MAC address using the best available method
 */
fun applyMacAddress(mac: String) {
    val (out, err) = when {
        hasMacChanger() -> {
            ShellUtils.execRootWithError("macchanger -r $INTERFACE")
        }
        hasBusyBox() -> {
            ShellUtils.execRootWithError(
                "busybox ifconfig $INTERFACE down; " +
                "busybox ifconfig $INTERFACE hw ether $mac; " +
                "busybox ifconfig $INTERFACE up"
            )
        }
        else -> {
            ShellUtils.execRootWithError(
                "ip link set $INTERFACE down; " +
                "ip link set $INTERFACE address $mac; " +
                "ip link set $INTERFACE up"
            )
        }
    }
    if (err.isNotEmpty()) {
        throw RuntimeException("Error applying MAC: $err")
    }
}

/**
 * Checks if macchanger is available
 */
private fun hasMacChanger(): Boolean {
    return try {
        ShellUtils.execRoot("which macchanger").isNotEmpty()
    } catch (_: Exception) {
        false
    }
}

/**
 * Checks if BusyBox is available
 */
private fun hasBusyBox(): Boolean {
    return try {
        ShellUtils.execRoot("which busybox").isNotEmpty()
    } catch (_: Exception) {
        false
    }
}

}

