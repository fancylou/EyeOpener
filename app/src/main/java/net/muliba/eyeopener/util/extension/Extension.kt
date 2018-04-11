package net.muliba.accounting.utils.ext

import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.ColorInt

/**
 * Created by fancy on 2017/8/21.
 * Copyright © 2017 O2. All rights reserved.
 */


fun Double.twoDecimalString(): String = String.format("%.2f", this)

fun Float.twoDecimalString(): String = String.format("%.2f", this)


fun Context.color2RGB(@ColorInt color: Int): IntArray = intArrayOf((color and 0xff0000 shr 16), (color and 0x00ff00 shr 8), (color and 0x0000ff))
/**
 * RGB 模式转换成 YUV 模式，而 Y 是明亮度（灰阶），因此只需要获得 Y 的值而判断他是否足够亮就可以了
 */
fun Context.isLightColor(@ColorInt color: Int): Boolean {
    val a = color2RGB(color)
    val grayLevel = (a[0] * 0.299 + a[1] * 0.587 + a[2] * 0.114).toInt()
    if (grayLevel >= 192) {
        return true
    }
    return false
}

inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}