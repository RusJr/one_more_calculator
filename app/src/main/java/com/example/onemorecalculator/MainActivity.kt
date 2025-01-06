package com.example.onemorecalculator

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    private lateinit var calc: Calculator
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById<TextView>(R.id.resultTv)
        calc = Calculator(10)
    }

    fun onInput(view: View) {
        view.performHapticFeedback(1)
        val button = view as Button
        val number = button.getText().toString()
        calc.input(number)
        textView.text = calc.getScreenNumber()
    }

    fun onAC(view: View) {
        view.performHapticFeedback(1)
        calc.reset()
        textView.text = calc.getScreenNumber()
    }

    fun onBackspace(view: View) {
        view.performHapticFeedback(1)
        calc.backspace()
        textView.text = calc.getScreenNumber()
    }

    fun onInputPi(view: View) {
        view.performHapticFeedback(1)
        calc.inputPi()
        textView.text = calc.getScreenNumber()
    }

    fun onAdd(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.ADDITION)
        textView.text = calc.getScreenNumber()
        runTextViewAnimation()
    }

    fun onSubtract(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.SUBTRACTION)
        textView.text = calc.getScreenNumber()
        runTextViewAnimation()
    }

    fun onMultiply(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.MULTIPLICATION)
        textView.text = calc.getScreenNumber()
        runTextViewAnimation()
    }

    fun onDivide(view: View) {
        view.performHapticFeedback(1)
        calc.setOperation(Operation.DIVISION)
        textView.text = calc.getScreenNumber()
        runTextViewAnimation()
    }

    fun onEquals(view: View) {
        view.performHapticFeedback(1)
        try {
            calc.equals()
        } catch (e: CustomZeroDivisionException) {
            Toast.makeText(this, "ZERO DIVISION!", Toast.LENGTH_SHORT).show()
        }

        textView.text = calc.getScreenNumber()
    }

    private fun runTextViewAnimation() {
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 100
        textView.startAnimation(fadeOut)
    }
}
