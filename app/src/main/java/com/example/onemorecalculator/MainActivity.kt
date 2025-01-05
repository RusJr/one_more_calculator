package com.example.onemorecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    private lateinit var calc: Calculator
    private lateinit var text_view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_view = findViewById<TextView>(R.id.resultTv)
        calc = Calculator(10)
    }

    fun onInput(view: View) {
        view.performHapticFeedback(1)
        val button = view as Button
        val number = button.getText().toString()
        calc.input(number)
        text_view.text = calc.getScreenNumber()
    }

    fun onAC(view: View) {
        view.performHapticFeedback(1)
        calc.reset()
        text_view.text = calc.getScreenNumber()
    }

    fun onBackspace(view: View) {
        view.performHapticFeedback(1)
        calc.backspace()
        text_view.text = calc.getScreenNumber()
    }

    fun onInputPi(view: View) {
        view.performHapticFeedback(1)
        calc.inputPi()
        text_view.text = calc.getScreenNumber()
    }

    fun onAdd(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.ADDITION)
        text_view.text = calc.getScreenNumber()
    }

    fun onSubtract(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.SUBTRACTION)
        text_view.text = calc.getScreenNumber()
    }

    fun onMultiply(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.MULTIPLICATION)
        text_view.text = calc.getScreenNumber()
    }

    fun onDivide(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.DIVISION)
        text_view.text = calc.getScreenNumber()
    }

    fun onEquals(view: View) {
        view.performHapticFeedback(1)
        calc.equals()
        text_view.text = calc.getScreenNumber()
    }
}
