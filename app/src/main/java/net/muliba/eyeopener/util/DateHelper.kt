package net.muliba.accounting.utils.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by fancy on 2017/3/2.
 */


fun Date.format(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}

object DateHelper {
    val sdf_string = "yyyy-MM-dd"
    val md_string = "MM-dd"
    val default_string = "yyyy-MM-dd HH:mm:ss"

    fun convertStringToDate(time: String, format: String): Date {
        return SimpleDateFormat(format).parse(time)
    }


    fun formatDate(date: Date, format: String): String {
        return SimpleDateFormat(format).format(date)
    }

    fun convertStringToCalendar(dateString: String): Calendar {
        val date = convertStringToDate(dateString, sdf_string)
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    fun formatDate(cal: Calendar): String {
        return SimpleDateFormat(sdf_string).format(cal.time)
    }

    /**
     * @param date yyyy-MM-dd
     */
    fun convertDayToWeek(date: String): String {
        val day = SimpleDateFormat(sdf_string).parse(date)
        if (day != null) {
            val calendar = Calendar.getInstance()
            calendar.time = day
            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return when (weekDay) {
                Calendar.SUNDAY -> "周日"
                Calendar.MONDAY -> "周一"
                Calendar.TUESDAY -> "周二"
                Calendar.WEDNESDAY -> "周三"
                Calendar.THURSDAY -> "周四"
                Calendar.FRIDAY -> "周五"
                Calendar.SATURDAY -> "周六"
                else -> ""
            }
        }
        return ""
    }

    fun now(): String {
        return SimpleDateFormat(default_string).format(Date())
    }

    fun thisYear(): String {
        return SimpleDateFormat("yyyy").format(Date())
    }

    fun thisMonth(): String {
        return SimpleDateFormat("MM").format(Date())
    }

    fun today(): String {
        return SimpleDateFormat(sdf_string).format(Date())
    }

}