package com.example.snakegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SnakeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val snake = mutableListOf<Point>()
    private var food = Point(0, 0)
    private var direction = Direction.RIGHT
    private var gridSize = 20
    private var score = 0

    private val snakeHeadPaint = Paint().apply {
        color = context.getColor(R.color.snake_head)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val snakeBodyPaint = Paint().apply {
        color = context.getColor(R.color.snake_body)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val foodPaint = Paint().apply {
        color = context.getColor(R.color.food)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val gridPaint = Paint().apply {
        color = context.getColor(R.color.grid)
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    private var cellWidth = 0f
    private var cellHeight = 0f
    private var cols = 0
    private var rows = 0

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    data class Point(val x: Int, val y: Int)

    fun setDirection(newDirection: Direction) {
        val opposite = when (newDirection) {
            Direction.UP -> Direction.DOWN
            Direction.DOWN -> Direction.UP
            Direction.LEFT -> Direction.RIGHT
            Direction.RIGHT -> Direction.LEFT
        }
        if (direction != opposite) {
            direction = newDirection
        }
    }

    fun initGame() {
        snake.clear()
        snake.add(Point(cols / 2, rows / 2))
        snake.add(Point(cols / 2 - 1, rows / 2))
        snake.add(Point(cols / 2 - 2, rows / 2))
        direction = Direction.RIGHT
        score = 0
        placeFood()
    }

    private fun placeFood() {
        do {
            food = Point(
                (1 until cols - 1).random(),
                (1 until rows - 1).random()
            )
        } while (snake.any { it.x == food.x && it.y == food.y })
    }

    fun moveSnake(): Boolean {
        val head = snake.first()
        val newHead = when (direction) {
            Direction.UP -> Point(head.x, head.y - 1)
            Direction.DOWN -> Point(head.x, head.y + 1)
            Direction.LEFT -> Point(head.x - 1, head.y)
            Direction.RIGHT -> Point(head.x + 1, head.y)
        }

        if (newHead.x < 0 || newHead.x >= cols || newHead.y < 0 || newHead.y >= rows) {
            return false
        }

        if (snake.any { it.x == newHead.x && it.y == newHead.y }) {
            return false
        }

        snake.add(0, newHead)

        if (newHead.x == food.x && newHead.y == food.y) {
            score++
            placeFood()
        } else {
            snake.removeAt(snake.size - 1)
        }

        return true
    }

    fun getScore(): Int = score

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cellWidth = w.toFloat() / cols
        cellHeight = h.toFloat() / rows
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        cols = size / gridSize
        rows = (MeasureSpec.getSize(heightMeasureSpec)) / gridSize
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (cols == 0 || rows == 0) return

        cellWidth = width.toFloat() / cols
        cellHeight = height.toFloat() / rows

        snake.forEachIndexed { index, point ->
            val paint = if (index == 0) snakeHeadPaint else snakeBodyPaint
            val margin = 2f
            canvas.drawRoundRect(
                point.x * cellWidth + margin,
                point.y * cellHeight + margin,
                (point.x + 1) * cellWidth - margin,
                (point.y + 1) * cellHeight - margin,
                cellWidth / 4,
                cellHeight / 4,
                paint
            )
        }

        canvas.drawCircle(
            (food.x + 0.5f) * cellWidth,
            (food.y + 0.5f) * cellHeight,
            minOf(cellWidth, cellHeight) / 3,
            foodPaint
        )
    }
}
