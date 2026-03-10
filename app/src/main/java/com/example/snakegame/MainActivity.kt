package com.example.snakegame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var snakeView: SnakeView
    private lateinit var scoreText: TextView
    private var gameRunning = false
    private val gameDelay = 150L

    private val gameRunnable = object : Runnable {
        override fun run() {
            if (gameRunning) {
                val alive = snakeView.moveSnake()
                if (alive) {
                    scoreText.text = "分数: ${snakeView.getScore()}"
                    snakeView.invalidate()
                    snakeView.postDelayed(this, gameDelay)
                } else {
                    gameOver()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        snakeView = findViewById(R.id.snakeView)
        scoreText = findViewById(R.id.scoreText)

        setupButtons()

        snakeView.post {
            snakeView.initGame()
            snakeView.invalidate()
        }
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnUp).setOnClickListener {
            snakeView.setDirection(SnakeView.Direction.UP)
            startGameIfNotRunning()
        }

        findViewById<Button>(R.id.btnDown).setOnClickListener {
            snakeView.setDirection(SnakeView.Direction.DOWN)
            startGameIfNotRunning()
        }

        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            snakeView.setDirection(SnakeView.Direction.LEFT)
            startGameIfNotRunning()
        }

        findViewById<Button>(R.id.btnRight).setOnClickListener {
            snakeView.setDirection(SnakeView.Direction.RIGHT)
            startGameIfNotRunning()
        }
    }

    private fun startGameIfNotRunning() {
        if (!gameRunning) {
            gameRunning = true
            snakeView.post(gameRunnable)
        }
    }

    private fun gameOver() {
        gameRunning = false
        val score = snakeView.getScore()
        Toast.makeText(this, "游戏结束！最终分数: $score", Toast.LENGTH_LONG).show()
        
        snakeView.postDelayed({
            snakeView.initGame()
            scoreText.text = "分数: 0"
            snakeView.invalidate()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        gameRunning = false
        snakeView.removeCallbacks(gameRunnable)
    }
}
