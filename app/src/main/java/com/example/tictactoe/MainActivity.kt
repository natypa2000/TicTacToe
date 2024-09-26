package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var statusText: TextView
    private lateinit var playAgainButton: Button
    private var currentPlayer = "X"
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        playAgainButton = findViewById(R.id.playAgainButton)

        buttons = Array(3) { row ->
            Array(3) { col ->
                findViewById<Button>(
                    resources.getIdentifier(
                        "button$row$col",
                        "id",
                        packageName
                    )
                ).apply {
                    setOnClickListener { onCellClicked(row, col) }
                }
            }
        }

        playAgainButton.setOnClickListener { resetGame() }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (gameActive && buttons[row][col].text.isEmpty()) {
            buttons[row][col].text = currentPlayer
            if (checkForWin()) {
                statusText.text = "Player $currentPlayer wins!"
                endGame()
            } else if (isBoardFull()) {
                statusText.text = "It's a draw!"
                endGame()
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Player $currentPlayer's turn"
            }
        }
    }

    private fun checkForWin(): Boolean {
        for (i in 0..2) {
            if (buttons[i][0].text.isNotEmpty() &&
                buttons[i][0].text == buttons[i][1].text &&
                buttons[i][0].text == buttons[i][2].text
            ) return true
            if (buttons[0][i].text.isNotEmpty() &&
                buttons[0][i].text == buttons[1][i].text &&
                buttons[0][i].text == buttons[2][i].text
            ) return true
        }
        if (buttons[0][0].text.isNotEmpty() &&
            buttons[0][0].text == buttons[1][1].text &&
            buttons[0][0].text == buttons[2][2].text
        ) return true
        if (buttons[0][2].text.isNotEmpty() &&
            buttons[0][2].text == buttons[1][1].text &&
            buttons[0][2].text == buttons[2][0].text
        ) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        return buttons.all { row -> row.all { it.text.isNotEmpty() } }
    }

    private fun endGame() {
        gameActive = false
        playAgainButton.visibility = View.VISIBLE
    }

    private fun resetGame() {
        buttons.forEach { row -> row.forEach { it.text = "" } }
        currentPlayer = "X"
        gameActive = true
        statusText.text = "Player X's turn"
        playAgainButton.visibility = View.GONE
    }
}