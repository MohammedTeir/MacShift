// File: BootReceiver.kt 
package com.macshift.receivers

import android.content.BroadcastReceiver 
import android.content.Context 
import android.content.Intent 
import com.macshift.utils.PreferencesHelper 
import com.macshift.utils.MacUtils

class BootReceiver : BroadcastReceiver() { override fun onReceive(context: Context, intent: Intent) { if (intent.action == Intent.ACTION_BOOT_COMPLETED) { // Check if auto-spoof on boot is enabled if (PreferencesHelper.isAutoSpoofOnBoot()) { // Get saved original or generate random if none val macToApply = PreferencesHelper.getOriginalMac() ?: MacUtils.generateRandomMac() try { MacUtils.applyMacAddress(macToApply) } catch (e: Exception) { // Optionally log error via Log.e } } } } }

// Manifest snippet (AndroidManifest.xml) /* <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<application android:name=".App" ... > <receiver
android:name=".receivers.BootReceiver"
android:enabled="true"
android:exported="true"
android:permission="android.permission.RECEIVE_BOOT_COMPLETED"> <intent-filter> <action android:name="android.intent.action.BOOT_COMPLETED" /> </intent-filter> </receiver> ... </application> */

