package com.furkanbalci.travelguide.util

import android.app.DatePickerDialog
import android.widget.EditText
import com.furkanbalci.travelguide.util.PreferencesUtil.set
import java.util.*

object DateUtil {

    fun showDatePickerDialog(editText: EditText, isEnd: Boolean) {

        val date: Date = if (isEnd) {
            Date(PreferencesUtil.defaultPrefs(editText.context).getLong("trip-destination-ending-time", Date().time))
        } else {
            Date(PreferencesUtil.defaultPrefs(editText.context).getLong("trip-destination-starting-time", Date().time))
        }

        // Get Current Date
        val c = Calendar.getInstance()
        c.time = date
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            editText.context, { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                if (isEnd) {
                    PreferencesUtil.defaultPrefs(editText.context)["trip-destination-ending-time"] =
                        selectedDate.timeInMillis
                } else {
                    PreferencesUtil.defaultPrefs(editText.context)["trip-destination-starting-time"] =
                        selectedDate.timeInMillis
                }
                editText.setText("${dayOfMonth}/${monthOfYear + 1}/${year}")
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()

    }

}