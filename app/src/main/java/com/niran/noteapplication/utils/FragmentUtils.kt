package com.niran.noteapplication.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.niran.noteapplication.R

class FragmentUtils {
    companion object {
        fun Fragment.hideKeyBoard() {
            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as
                    InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }

        fun Fragment.showUndoSnackBar(onUndo: () -> Unit) = view?.let {
            Snackbar.make(
                it,
                getString(R.string.note_deleted_successfully),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(R.string.undo) { onUndo() }
                show()
            }
        }
    }
}
