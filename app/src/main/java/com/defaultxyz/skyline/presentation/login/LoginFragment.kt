package com.defaultxyz.skyline.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.FragmentLoginBinding
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.extensions.showToast
import com.defaultxyz.skyline.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    private val viewModel by lazy { provideViewModel<LoginViewModel>(factory) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentLoginBinding>(
        inflater, R.layout.fragment_login, container, false
    ).apply {
        viewModel = this@LoginFragment.viewModel
        setLifecycleOwner(this@LoginFragment)
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.login)

        registrationButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        viewModel.resultMessage.observe(this, Observer { result ->
            when (result.data) {
                LoginState.SUCCESS -> findNavController().navigate(R.id.action_loginFragment_to_mapActivity)
                LoginState.FAILED, LoginState.EMPTY -> context?.showToast(result.info)
            }
        })
    }
}
