package com.defaultxyz.skyline.presentation.main

import androidx.lifecycle.ViewModel
import com.defaultxyz.skyline.domain.UserRepository
import com.defaultxyz.skyline.domain.model.User
import com.defaultxyz.skyline.utils.ActionResult
import io.reactivex.Single
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    fun fetchUser(): Single<ActionResult<User?>> = repository.isUserAuthorized()

}