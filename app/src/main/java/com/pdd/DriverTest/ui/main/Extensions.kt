package com.pdd.DriverTest.ui.main

import android.view.View

fun View.show(): View{
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
    return this
}

