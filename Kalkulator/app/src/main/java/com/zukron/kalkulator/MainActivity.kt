package com.zukron.kalkulator

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Project name is Kalkulator
 * Created by Zukron Alviandy R on 9/2/2020
 * Contact me if any issues on zukronalviandy@gmail.com
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var symbols: String = ""
    private var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_number_0.setOnClickListener(this)
        btn_number_1.setOnClickListener(this)
        btn_number_2.setOnClickListener(this)
        btn_number_3.setOnClickListener(this)
        btn_number_4.setOnClickListener(this)
        btn_number_5.setOnClickListener(this)
        btn_number_6.setOnClickListener(this)
        btn_number_7.setOnClickListener(this)
        btn_number_8.setOnClickListener(this)
        btn_number_9.setOnClickListener(this)

        btn_c_symbol.setOnClickListener(this)
        btn_time_symbol.setOnClickListener(this)
        btn_divide_symbol.setOnClickListener(this)
        btn_minus_symbol.setOnClickListener(this)
        btn_plus_symbol.setOnClickListener(this)

        btn_backspace.setOnClickListener(this)
        btn_dot.setOnClickListener(this)
        btn_equal_symbol.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_number_0 -> text += "0"
            R.id.btn_number_1 -> text += "1"
            R.id.btn_number_2 -> text += "2"
            R.id.btn_number_3 -> text += "3"
            R.id.btn_number_4 -> text += "4"
            R.id.btn_number_5 -> text += "5"
            R.id.btn_number_6 -> text += "6"
            R.id.btn_number_7 -> text += "7"
            R.id.btn_number_8 -> text += "8"
            R.id.btn_number_9 -> text += "9"
            R.id.btn_time_symbol -> text = calculateSymbol(text, 'x')
            R.id.btn_divide_symbol -> text = calculateSymbol(text, '/')
            R.id.btn_minus_symbol -> text = calculateSymbol(text, '-')
            R.id.btn_plus_symbol -> text = calculateSymbol(text, '+')
            R.id.btn_c_symbol -> clear()
            R.id.btn_backspace -> text = backspace(text)
            R.id.btn_dot -> text = dot(text)
            R.id.btn_equal_symbol -> calculate(tv_calculate_and_result.text.toString())
        }

        // if result is infinity
        if (tv_calculate_and_result.text == "Infinity") {
            clear()
        }

        tv_calculate_and_result.text = text
    }

    /***
     * berfungsi untuk memotong simbol yang double dan simbol yang bertumpuk pada perhitungan
     */
    private fun calculateSymbol(text: String, symbol: Char): String {
        if (!tv_calculate_and_result.text.isBlank()) {
            val length = text.length
            val charEnd = text[length - 1]

            // collect symbol to calculate
            symbols += symbol

            if (charEnd == 'x' || charEnd == '/' || charEnd == '+' || charEnd == '-') {
                return text.substring(0, (length - 1)) + symbol
            }

            return text + symbol
        }
        return ""
    }

    private fun calculate(text: String) {
        if (!text.isBlank()) {
            val textArray = text.split(Regex("[x/+-]"))
            val symbolLength = symbols.length - 1
            val numbers: MutableList<Double> = textArray.map {
                it.toDouble()
            } as MutableList<Double>
            var result: Double? = null

            for (i in 0..symbolLength) {
                if (symbols[i] == '/') {
                    result = numbers[i] / numbers[i + 1]
                    numbers[i + 1] = result
                }

                if (symbols[i] == 'x') {
                    result = numbers[i] * numbers[i + 1]
                    numbers[i + 1] = result
                }

                if (symbols[i] == '+') {
                    result = numbers[i] + numbers[i + 1]
                    numbers[i + 1] = result
                }

                if (symbols[i] == '-') {
                    result = numbers[i] - numbers[i + 1]
                    numbers[i + 1] = result
                }
            }

            // cari .0 angka dibelakang, jika tidak hanya akan memparsing saja
            val strResult = if (result.toString().matches(Regex(".*[.0]$"))) {
                result.toString().replace(".0", "")
            } else {
                result.toString()
            }

            // create history
            tv_history.text = text

            // clear all symbol and text change to result
            this.symbols = ""
            this.text = strResult
            tv_calculate_and_result.text = strResult
        }
    }

    private fun clear() {
        symbols = ""
        text = ""
        tv_history.text = ""
    }

    private fun dot(text: String): String {
        val length = text.length
        val charEnd: Char = text[length - 1]

        return if (charEnd == 'x' || charEnd == '/' || charEnd == '+' || charEnd == '-') {
            text + "0."
        } else {
            "$text."
        }
    }

    private fun backspace(text: String): String {
        return if (text.isBlank()) {
            ""
        } else {
            val length = text.length
            text.substring(0, (length - 1))
        }
    }
}