package com.defaultxyz.skyline.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.FragmentRegistrationBinding
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.utils.BaseFragment

class RegistrationFragment : BaseFragment() {
    private val viewModel by lazy { provideViewModel<LoginViewModel>(factory) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentRegistrationBinding>(
        inflater, R.layout.fragment_registration, container, false
    ).apply {
        viewModel = this@RegistrationFragment.viewModel
    }.root
}
