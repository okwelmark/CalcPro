package com.example.calcpro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var resultTv: TextView
    private lateinit var solutionTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTv = findViewById(R.id.result_tv)
        solutionTv = findViewById(R.id.solution_tv)

        // Set up number buttons
        val numberButtons = arrayOf(
            R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four,
            R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine
        )

        for (buttonId in numberButtons) {
            findViewById<MaterialButton>(buttonId).setOnClickListener {
                val buttonText = (it as MaterialButton).text
                val currentText = resultTv.text
                resultTv.text = "$currentText$buttonText"
            }
        }

        // Set up arithmetic buttons
        val arithmeticButtons = arrayOf(
            R.id.addition, R.id.subtraction, R.id.multiplication, R.id.division
        )

        for (buttonId in arithmeticButtons) {
            findViewById<MaterialButton>(buttonId).setOnClickListener {
                val operator = (it as MaterialButton).text
                val expression = resultTv.text
                resultTv.text = "$expression $operator "
            }
        }

        // Set up equals button
        val equalsButton = findViewById<MaterialButton>(R.id.equals)
        equalsButton.setOnClickListener {
            val expression = resultTv.text.toString()
            val result = evaluateExpression(expression)
            solutionTv.text = result
        }

        // Set up delete button
        val deleteButton = findViewById<MaterialButton>(R.id.delete)
        deleteButton.setOnClickListener {
            val currentText = resultTv.text.toString()
            if (currentText.isNotEmpty()) {
                resultTv.text = currentText.substring(0, currentText.length - 1)
            }
        }
    }

    private fun evaluateExpression(expression: String): String {
        // Split expression based on space
        val parts = expression.split(" ")
        var result = 0.0
        var operator = ""

        for (part in parts) {
            when {
                part == "+" || part == "-" || part == "*" || part == "/" -> {
                    operator = part
                }
                operator.isEmpty() -> {
                    result = part.toDouble()
                }
                operator.isNotEmpty() -> {
                    val operand = part.toDouble()
                    when {
                        operator == "/" && operand == 0.0 -> {
                            return "Syntax error"
                        }
                        else -> {
                            result = when (operator) {
                                "+" -> result + operand
                                "-" -> result - operand
                                "*" -> result * operand
                                "/" -> result / operand
                                else -> result
                            }
                            operator = ""
                        }
                    }
                }
            }
        }
        return result.toString()
    }
}
