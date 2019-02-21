package com.defaultxyz.skyline.presentation.login

import android.os.Bundle
import androidx.navigation.findNavController
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.utils.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
