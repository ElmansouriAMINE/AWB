package com.example.testoo.Utils

import android.app.AlertDialog
import android.content.Context


fun showNoInternetDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("No Internet Connection")
        .setMessage("Please check your internet connection and try again.")
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}
