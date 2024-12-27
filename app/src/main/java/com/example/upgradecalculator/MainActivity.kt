package com.example.upgradecalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var editTextInput: EditText
    private lateinit var editTextResult: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Настройка Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        editTextInput = findViewById(R.id.editTextInput)
        editTextResult = findViewById(R.id.editTextResult)

        // Настройка кнопок калькулятора
        setupCalculatorButtons()
    }

    private fun setupCalculatorButtons() {
        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEquals, R.id.buttonClear
        )

        for (id in buttonIds) {
            val button: Button = findViewById(id)
            button.setOnClickListener { onCalculatorButtonClick(it) }
        }
    }

    private fun onCalculatorButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "=" -> calculateResult()
            "C" -> clearInput()
            else -> appendToInput(buttonText)
        }
    }

    private fun appendToInput(value: String) {
        editTextInput.setText(editTextInput.text.toString() + value)
    }

    private fun calculateResult() {
        val input = editTextInput.text.toString()
        try {
            val result = evaluateExpression(input)
            editTextResult.setText(getString(R.string.result_format, result.toString()))
        } catch (e: Exception) {
            editTextResult.setText(R.string.error_message)
        }
    }

    private fun clearInput() {
        editTextInput.setText("")
        editTextResult.setText("")
    }

    private fun evaluateExpression(expression: String): Double {
        val expressionBuilder = ExpressionBuilder(expression).build()
        return expressionBuilder.evaluate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity() // Закрывает все активности и завершает приложение
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}