package com.defaultxyz.skyline.custom

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.fragment.app.DialogFragment
import com.defaultxyz.skyline.R
import java.util.*

class DateEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), View.OnClickListener {
    private val calendar = Calendar.getInstance()

    var year: Int
        get() = calendar.get(Calendar.YEAR)
        set(value) = calendar.set(Calendar.YEAR, value)

    var month: Int
        get() = calendar.get(Calendar.MONTH)
        set(value) = calendar.set(Calendar.MONTH, value)

    var day: Int
        get() = calendar.get(Calendar.DAY_OF_MONTH)
        set(value) = calendar.set(Calendar.DAY_OF_MONTH, value)

    init {
        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        (context as? AppCompatActivity)?.supportFragmentManager?.let { fm ->
            DatePickerFragment.newInstance(this)
                .apply {
                    show(fm, "")
                }
        }
    }

    fun setDate(year: Int, month: Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day
        updateText()
    }

    fun setDate(date: Date) {
        calendar.time = date
        updateText()
    }

    private fun updateText() {
        text = DateFormat.format("dd/MM/yyyy", calendar)
    }

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["date", "dateAttrChanged"], requireAll = false)
        fun DateEditText.setDate(date: Date?, dateAttrChanged: InverseBindingListener?) {
            date?.takeIf { this.calendar.time != date }?.let {
                dateAttrChanged?.onChange()
                setDate(date)
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "date", event = "dateAttrChanged")
        fun DateEditText.getDate(): Date = calendar.time
    }
}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var parent: DateEditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        DatePickerDialog(
            parent.context, R.style.Theme_AppCompat_Light_Dialog,
            this, parent.year, parent.month, parent.day
        )

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        parent.setDate(year, month, dayOfMonth)
    }

    companion object {
        fun newInstance(parent: DateEditText) = DatePickerFragment().apply {
            this.parent = parent
        }
    }
}