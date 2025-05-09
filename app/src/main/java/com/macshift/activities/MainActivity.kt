// File: MainActivity.kt 
package com.macshift.activities

import android.os.Bundle 
import androidx.activity.viewModels 
import androidx.appcompat.app.AppCompatActivity 
import com.macshift.databinding.ActivityMainBinding 
import com.macshift.viewmodel.MainViewModel 
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() { private lateinit var binding: ActivityMainBinding private val viewModel: MainViewModel by viewModels()

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Observe ViewModel LiveData
    viewModel.isRooted.observe(this) { rooted ->
        binding.rootStatus.text = if (rooted) "Root Access: Granted" else "Root Access: Denied"
    }
    viewModel.currentMac.observe(this) { mac ->
        binding.currentMac.text = mac
    }
    viewModel.operationResult.observe(this) { result ->
        Snackbar.make(binding.root, result, Snackbar.LENGTH_SHORT).show()
    }
    viewModel.errorMessage.observe(this) { err ->
        Snackbar.make(binding.root, "Error: $err", Snackbar.LENGTH_LONG).show()
    }

    // Button actions
    binding.randomButton.setOnClickListener {
        viewModel.spoofRandomMac()
    }
    binding.customButton.setOnClickListener {
        val input = binding.customInput.text.toString().trim()
        viewModel.spoofCustomMac(input)
    }
    binding.restoreButton.setOnClickListener {
        viewModel.restoreOriginalMac()
    }
    binding.autoBootSwitch.setOnCheckedChangeListener { _, isChecked ->
        viewModel.setAutoSpoofOnBoot(isChecked)
    }

    // Initial load
    viewModel.initialize()
}

}

