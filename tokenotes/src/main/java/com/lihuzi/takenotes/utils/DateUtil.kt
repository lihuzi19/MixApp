package com.lihuzi.takenotes.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap

class DateUtil {
    companion object {
        val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm"
        val FORMAT_MAP = ConcurrentHashMap<String, SimpleDateFormat>()

        fun getTimeFormatString(dateTime: Long): String {
            return getTimeFormatString(DEFAULT_FORMAT, dateTime)
        }

        fun getTimeFormatString(format: String, dateTime: Long): String {
            return getFormat(format).format(dateTime)
        }

        private fun getFormat(format: String): SimpleDateFormat {
            var key = format + Thread.currentThread().id
            return if (FORMAT_MAP.contains(key)) {
                FORMAT_MAP[key]!!
            } else {
                var simpleDateFormat = SimpleDateFormat(format)
                FORMAT_MAP[key] = simpleDateFormat
                simpleDateFormat
            }
        }

        fun getMonthMiniTime(calendar: Calendar): Calendar {
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE))
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE))
            calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND))
            calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND))
            return calendar
        }

        fun getMonthMaxTime(calendar: Calendar): Calendar {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE))
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE))
            calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND))
            calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND))
            calendar.timeInMillis = calendar.timeInMillis - 1
            return calendar
        }
    }
}