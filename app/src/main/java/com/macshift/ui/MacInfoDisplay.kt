// File: MacInfoDisplay.kt 
package com.macshift.ui

import android.content.Context 
import android.util.AttributeSet 
import android.view.LayoutInflater 
import android.widget.LinearLayout 
import android.widget.TextView 
import com.macshift.R

class MacInfoDisplay @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : LinearLayout(context, attrs, defStyleAttr) {

private val macTextView: TextView
private val rootStatusTextView: TextView

init {
    LayoutInflater.from(context).inflate(R.layout.view_mac_info_display, this, true)
    orientation = VERTICAL

    macTextView = findViewById(R.id.text_mac_address)
    rootStatusTextView = findViewById(R.id.text_root_status)
}

fun setMacAddress(mac: String) {
    macTextView.text = context.getString(R.string.mac_address_format, mac)
}

fun setRootStatus(isRooted: Boolean) {
    rootStatusTextView.text = if (isRooted) {
        context.getString(R.string.root_status_true)
    } else {
        context.getString(R.string.root_status_false)
    }
}

}

