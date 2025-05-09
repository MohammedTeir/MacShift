// File: CustomMacInput.kt 
package com.macshift.ui

import android.content.Context 
import android.text.Editable 
import android.text.TextWatcher 
import android.util.AttributeSet 
import android.view.LayoutInflater 
import android.widget.EditText 
import android.widget.LinearLayout 
import com.macshift.R

class CustomMacInput @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : LinearLayout(context, attrs, defStyleAttr) {

private val inputField: EditText

init {
    orientation = VERTICAL
    LayoutInflater.from(context).inflate(R.layout.view_custom_mac_input, this, true)
    inputField = findViewById(R.id.edit_custom_mac)
}

/**
 * Returns the current text in the input field
 */
fun getMacInput(): String {
    return inputField.text.toString().trim()
}

/**
 * Validates the MAC input format and optionally displays an error
 */
fun setValidationListener(validate: (String) -> Boolean, onInvalid: (String) -> Unit) {
    inputField.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val text = s.toString().trim()
            if (text.isNotEmpty() && !validate(text)) {
                onInvalid(text)
            }
        }
    })
}

}

