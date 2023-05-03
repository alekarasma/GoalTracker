package com.asmaa.goaltracker.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.asmaa.goaltracker.R

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_actvity)
    }

    open fun showPopUp( context : Context, view:View)
    {
        val popupView = LayoutInflater.from(context).inflate(R.layout.popup_window, null)
        val popup = PopupWindow(popupView, 180, 180)
        popup.contentView = popupView
        popup.width = LinearLayout.LayoutParams.WRAP_CONTENT
        popup.height = LinearLayout.LayoutParams.WRAP_CONTENT
        popup.isFocusable = true
        popup.showAtLocation(view, Gravity.CENTER, 0, 0)
    }
}