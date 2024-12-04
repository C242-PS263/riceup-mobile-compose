package com.c242ps263.core.utils

import android.util.Log
import com.c242ps263.core.BuildConfig
import java.util.Locale

object UtilFunctions {
    private val localeID = Locale("in", "ID")

    fun logE(message: String) {
        if (BuildConfig.DEBUG) Log.e("ERROR_IMO", message)
    }
}