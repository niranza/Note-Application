package com.niran.noteapplication.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class ActivityUtils {
    companion object {
        fun Activity.hideKeyBoard() {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as
                    InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}