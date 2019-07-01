package com.kaisalar.android_client.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.github.paolorotolo.appintro.model.SliderPagerBuilder
import com.kaisalar.android_client.R
import com.kaisalar.android_client.ui.authentication.AuthActivity
import com.kaisalar.android_client.util.PREF_DID_INTRO_SHOW
import com.kaisalar.android_client.util.PreferencesManager

class IntroActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        showIntroSlides()
    }

    private fun showIntroSlides() {
        val slide1 = SliderPagerBuilder()
            .title("Survey Master")
            .description("The world's most popular free online survey tool")
            .imageDrawable(R.drawable.intro_1)
            .bgColor(resources.getColor(R.color.color4))
            .build()

        val slide2 = SliderPagerBuilder()
            .title("Survey Master")
            .description("Get the answers you need")
            .imageDrawable(R.drawable.intro_2)
            .bgColor(resources.getColor(R.color.color4))
            .build()

        val slide3 = SliderPagerBuilder()
            .title("Survey Master")
            .description("Let's start today")
            .imageDrawable(R.drawable.intro_3)
            .bgColor(resources.getColor(R.color.color4))
            .build()

        addSlide(AppIntro2Fragment.newInstance(slide1))
        addSlide(AppIntro2Fragment.newInstance(slide2))
        addSlide(AppIntro2Fragment.newInstance(slide3))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        PreferencesManager.getInstance(this).putBoolean(PREF_DID_INTRO_SHOW, true)
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        PreferencesManager.getInstance(this).putBoolean(PREF_DID_INTRO_SHOW, true)
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
