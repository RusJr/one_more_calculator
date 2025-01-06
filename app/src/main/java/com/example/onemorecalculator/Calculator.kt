package com.example.onemorecalculator

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


const val PI = "3.1415926"

enum class Operation {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
}

enum class State {
    INITIAL,
    NUMBER_INPUT,
    WAITING_FOR_SECOND_NUMBER,
}

class CustomZeroDivisionException(message: String) : Exception(message)

class Calculator(val limit: Int) {

    private var state: State = State.INITIAL
    private var operation: Operation? = null
    private var firstNumber: String? = null
    private var screenNumber: String = "0"

    fun input(input: String) {
        validateInput(input)

        if (state == State.INITIAL || state == State.WAITING_FOR_SECOND_NUMBER) {
            if (input == ".") {
                screenNumber = "0."
            } else {
                screenNumber = input
            }
            state = State.NUMBER_INPUT

        } else if (state == State.NUMBER_INPUT) {
            if (screenNumber.length < limit) {
                if (
                    !(input == "." && screenNumber.contains("."))
                    && !(screenNumber == "0" && input == "0")
                ) {
                    screenNumber = "$screenNumber$input"
                }
            }
        }
    }

    private fun validateInput(input: String) {
        if (input == ".") {
            return
        }
        var number = input.toDoubleOrNull()
        if (number === null) {
            throw Exception("Invalid input")
        }

    }

    fun inputPi() {
        if (state == State.INITIAL || state == State.WAITING_FOR_SECOND_NUMBER) {
            input(PI)
        }
    }

    fun getScreenNumber(): String {
        return formatNumber(screenNumber, limit=limit)
    }

    fun backspace() {
        if (state == State.NUMBER_INPUT) {
            if (
                (screenNumber.length == 1) ||
                (screenNumber.length == 2 && screenNumber.startsWith("-"))
            ) {
                screenNumber = "0"
                if (operation === null) {
                    state = State.INITIAL
                } else {
                    state = State.WAITING_FOR_SECOND_NUMBER
                }
            } else {
                screenNumber = screenNumber.substring(0, screenNumber.length - 1)
            }
        }
    }
    fun reset() {
        state = State.INITIAL
        operation = null
        firstNumber = null
        screenNumber = "0"
    }

    fun setOperation(operationToSet: Operation) {
        operation = operationToSet
        state = State.WAITING_FOR_SECOND_NUMBER
        firstNumber = screenNumber

    }

    fun equals() {
        val screenNumberDecimal = screenNumber.toBigDecimal()
        if (operation == Operation.DIVISION && screenNumberDecimal == BigDecimal(0)) {
            reset()
            throw CustomZeroDivisionException("zero division")
        }

        if (operation !== null  && state == State.NUMBER_INPUT){
            val result: BigDecimal? = when (operation) {
                Operation.ADDITION -> firstNumber?.toBigDecimal()?.plus(
                    screenNumberDecimal
                )
                Operation.SUBTRACTION -> firstNumber?.toBigDecimal()?.minus(
                    screenNumberDecimal
                )
                Operation.MULTIPLICATION -> firstNumber?.toBigDecimal()?.times(
                    screenNumberDecimal
                )
                Operation.DIVISION -> firstNumber?.toBigDecimal()?.divide(
                    screenNumberDecimal, limit - 2, RoundingMode.HALF_UP
                )
                else -> throw Exception("Invalid operation")
            }

            screenNumber = result?.stripTrailingZeros().toString()
            firstNumber = null
            operation = null
        }
    }
}

fun formatNumber(input: String, limit: Int): String {
    val number = input.toDoubleOrNull()
    if (number === null) {
        throw Exception("Invalid input")
    }

    // to keep zeros on input like "1.00"
    if (input.contains(".") && input.endsWith("0")) {
        return input
    }

    val format = DecimalFormat("#." + "#".repeat(limit - 1))
    var formatted = format.format(number)

    if (input.endsWith(".")) {
        formatted = "$formatted."
    }

    if (formatted.length > limit) {
        return String.format("%.${limit - 3}e", number)
    }

    return formatted
}
