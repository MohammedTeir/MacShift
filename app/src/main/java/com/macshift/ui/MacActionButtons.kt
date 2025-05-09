// File: MacActionButtons.kt 
package com.macshift.ui

import android.content.Context 
import android.util.AttributeSet 
import android.view.LayoutInflater 
import android.widget.Button 
import android.widget.LinearLayout 
import com.macshift.R

class MacActionButtons @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : LinearLayout(context, attrs, defStyleAttr) {

val randomButton: Button
val customButton: Button
val restoreButton: Button

init {
    orientation = VERTICAL
    LayoutInflater.from(context).inflate(R.layout.view_mac_action_buttons, this, true)

    randomButton = findViewById(R.id.button_random_mac)
    customButton = findViewById(R.id.button_custom_mac)
    restoreButton = findViewById(R.id.button_restore_mac)
}

/**
 * Optionally, expose convenience methods to set click listeners
 */
fun setOnRandomClickListener(listener: OnClickListener) {
    randomButton.setOnClickListener(listener)
}

fun setOnCustomClickListener(listener: OnClickListener) {
    customButton.setOnClickListener(listener)
}

fun setOnRestoreClickListener(listener: OnClickListener) {
    restoreButton.setOnClickListener(listener)
}

}

