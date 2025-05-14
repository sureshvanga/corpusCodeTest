package com.example.corpuscodetest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.corpuscodetest.others.DataStoreManager
import com.example.corpuscodetest.view.HomeActivity
import com.example.corpuscodetest.view.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        DataStoreManager.init(applicationContext)

        val splashLayout = FrameLayout(this).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.splash_blue))
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            addView(TextView(context).apply {
                text = context.getString(R.string.corpus)
                textSize = 52f
                setTextColor(Color.WHITE)
                gravity = Gravity.CENTER
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            })
        }

        setContentView(splashLayout)


        lifecycleScope.launch {
            delay(10000)
            DataStoreManager.isLoggedIn.first().let { loggedIn ->
                val intent = if (loggedIn) {
                    Intent(this@MainActivity, HomeActivity::class.java)
                } else {
                    Intent(this@MainActivity, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}