package com.kaisalar.android_client.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaisalar.android_client.util.PREF_DID_INTRO_SHOW
import com.kaisalar.android_client.util.PreferencesManager
import com.kaisalar.android_client.data.webservice.AuthService
import com.kaisalar.android_client.ui.authentication.AuthActivity
import com.kaisalar.android_client.ui.intro.IntroActivity
import com.kaisalar.android_client.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!PreferencesManager.getInstance(this).getBoolean(PREF_DID_INTRO_SHOW)) {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        } else if (!AuthService.getInstance(this).isUserSigned()) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
