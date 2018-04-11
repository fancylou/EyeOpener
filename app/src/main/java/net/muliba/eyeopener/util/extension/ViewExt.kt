package net.muliba.accounting.utils.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by fancy on 2017/5/11.
 */


fun ViewGroup.inflate(layout: Int) : View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}
fun View.visible() {
    visibility = View.VISIBLE
}
fun View.gone() {
    visibility = View.GONE
}
fun View.inVisible() {
    visibility = View.INVISIBLE
}
fun TextView.text2String(): String = text.toString()