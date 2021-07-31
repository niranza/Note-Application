package com.niran.noteapplication.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.niran.noteapplication.R
import com.google.android.material.snackbar.Snackbar

object AppUtils {
    fun showSnackBar(rootView: View, stringId: Int) {
        val snack = Snackbar.make(
            rootView,
            stringId,
            Snackbar.LENGTH_SHORT
        )

        val view: View = snack.view
        val tv = view.findViewById<View>(R.id.snackbar_text) as TextView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER;
        } else {
            tv.gravity = Gravity.CENTER_HORIZONTAL;
        }

        snack.show()
    }

    fun hideKeyBoard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}