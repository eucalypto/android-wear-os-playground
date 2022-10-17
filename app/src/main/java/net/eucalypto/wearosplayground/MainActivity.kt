package net.eucalypto.wearosplayground

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import net.eucalypto.wearosplayground.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

     requestExceptionFromBatteryOptimization()

    }

    private fun requestExceptionFromBatteryOptimization() {
        val powerManager: PowerManager = getSystemService() ?: return
        logMethodCall()
        if (powerManager.isIgnoringBatteryOptimizations(packageName).not()) {
            Timber.d("Asking to be excluded from battery optimization.")
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:${packageName}")
            startActivity(intent)
        }
    }
}


inline fun logMethodCall() {
    val stackTraceElement = Thread.currentThread().stackTrace[3]
    Timber.d("${stackTraceElement.methodName}() called")
}