package net.muliba.accounting.utils.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.content.res.ResourcesCompat
import android.util.TypedValue

/**
 * Created by fancy on 2017/3/8.
 */


/**
 * 跳转
 */
inline fun <reified T : Activity> Activity.go(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.goWithRequestCode(bundle: Bundle? = null, requestCode: Int = 0) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Activity> Activity.goThenKill(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
    finish()
}

/**
 * 获取主题配置中的的颜色值
 */
@ColorInt
fun Activity.getColorFromAttribute(attrResId: Int): Int {
    val typeValue = TypedValue()
    try {
        theme.resolveAttribute(attrResId, typeValue, true)
    } catch (e: Exception) {
    }

    return ResourcesCompat.getColor(resources, typeValue.resourceId, null)
}

/**
 * 获取主题配置中的的颜色值的另外一种方式
 */
@ColorInt
fun Activity.getColorFromAttribute2(attrResId: Int): Int {
    return try {
        val array = theme.obtainStyledAttributes(intArrayOf(attrResId))
        val color = array.getColor(0, Color.TRANSPARENT)
        array.recycle()
        color
    } catch (e: Exception) {
        Color.TRANSPARENT
    }

}

fun Context.screenWidth(): Int {
    val dm = resources.displayMetrics
    return dm.widthPixels
}

fun Context.screenHeight(): Int {
    val dm = resources.displayMetrics
    return dm.heightPixels
}