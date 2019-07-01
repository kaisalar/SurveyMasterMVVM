package com.kaisalar.android_client.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kaisalar.android_client.R
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class AuthActivity : AppCompatActivity(), CoroutineScope {
    // Coroutine
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // Navigation
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        job = Job()

        navController = findNavController(R.id.auth_nav_host)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.signInFragment,
                R.id.signUpFragment,
                R.id.firstSignUpFragment,
                R.id.secondSignUpFragment,
                R.id.thirdSingUpFragment,
                R.id.loadingDialog
            )
        )
        authBottomNavigationView.setupWithNavController(navController)
        authToolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
