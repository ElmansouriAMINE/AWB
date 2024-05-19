package com.example.testoo.UI.Animation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.example.testoo.R

class ALodingDialog(context: Context) : Dialog(context) {

    init {
        val params: WindowManager.LayoutParams = window?.attributes!!
        params.gravity = Gravity.CENTER
        window?.attributes = params
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val view: View = LayoutInflater.from(context).inflate(R.layout.loding_layout, null)
        setContentView(view)
    }
}
