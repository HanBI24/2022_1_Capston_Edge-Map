package com.example.hotplenavigation.util.extension

import android.content.Context
import android.widget.Toast

// 20197138 장은지
fun removeHtmlTag(desc: String): String {
    val replaceRegex = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>".toRegex()
    return desc.replace(replaceRegex, "")
}

fun makeToast(context: Context?, msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
