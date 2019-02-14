package com.andrescoulson.mymatchs.config

import android.util.Log
import com.andrescoulson.mymatchs.model.Data
import com.andrescoulson.mymatchs.model.Match
import java.lang.StringBuilder
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Util {

    companion object {
        fun getDate(string: String): Date {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date = simpleDateFormat.parse(string)
            val strings = simpleDateFormat.format(date)
            date = simpleDateFormat.parse(strings)
            return date
        }

        fun getDates(string: String): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date = simpleDateFormat.parse(string)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val stringBuilder = StringBuilder()
            stringBuilder.append(getMonhYearShort(calendar.get(Calendar.MONTH)))
                .append(" ")
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(", ")
                .append(calendar.get(Calendar.YEAR))
                .append(" at ")
                .append(calendar.get(Calendar.HOUR_OF_DAY))
                .append(":")
                .append(
                    if (calendar.get(Calendar.MINUTE) > 9) calendar.get(Calendar.MINUTE) else "0" + calendar.get(
                        Calendar.MINUTE
                    )
                )

            return stringBuilder.toString()
        }

        fun getMonthYear(month: Int): String {
            var mes = " "
            val dfs = DateFormatSymbols()
            val months = dfs.months
            if (month in 0..11) {
                mes = months[month]
            }
            return mes
        }

        fun getMonhYearShort(month: Int): String {
            var mes = " "
            val dfs = DateFormatSymbols()
            val months = dfs.shortMonths
            if (month in 0..11) {
                mes = months[month]
            }
            return mes
        }

        fun getDayOfWeek(day: Int): String? {
            var mes = " "
            val dfs = DateFormatSymbols()
            val months = dfs.shortWeekdays
            mes = months[day]
            return mes
        }

        fun compareDate(date: Date, date1: Date): Boolean {
            val calendar = Calendar.getInstance()
            val calendar2 = Calendar.getInstance()
            calendar.time = date
            calendar2.time = date1
            return (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
        }

        fun getListData(matchs: List<Match>): ArrayList<Data> {
            var listDate = arrayListOf<Data>()
            try {

                val calendar = Calendar.getInstance()
                calendar.time = Util.getDate(matchs[0].date!!)

                val stringBuilder = StringBuilder()
                stringBuilder.append(Util.getMonthYear(calendar.get(Calendar.MONTH)))
                    .append(" ")
                    .append(calendar.get(Calendar.YEAR))
                listDate.add(Data(stringBuilder.toString(), null))

                for (i in matchs.indices) {
                    if (i + 1 <= matchs.size - 1 && !Util.compareDate(
                            Util.getDate(matchs[i].date!!),
                            Util.getDate(matchs[i + 1].date!!)
                        )
                    ) {

                        calendar.time = Util.getDate(matchs[i + 1].date!!)
                        listDate.add(Data(null, matchs[i]))
                        stringBuilder.clear()
                        stringBuilder.append(Util.getMonthYear(calendar.get(Calendar.MONTH)))
                            .append(" ")
                            .append(calendar.get(Calendar.YEAR))
                        Log.e("DATE", stringBuilder.toString())
                        listDate.add(Data(stringBuilder.toString(), null))

                    } else
                        listDate.add(Data(null, matchs[i]))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return listDate
        }

        fun containMont(match: Match, string: String): Boolean {
            val calendar = Calendar.getInstance()
            calendar.time = getDate(match.date!!)
            return getMonhYearShort(calendar.get(Calendar.MONTH)).toLowerCase().contains(string)

        }
    }
}