package com.defaultxyz.skyline.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> AppCompatActivity.provideViewModel(factory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, factory).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.provideViewModel(factory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, factory).get(T::class.java)

inline fun <reified T : Activity> Activity.startActivity(shouldFinishCurrent: Boolean = false) =
    Intent(this, T::class.java).apply {
        startActivity(this)
        if (shouldFinishCurrent) finish()
    }

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}