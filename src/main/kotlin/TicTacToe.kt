const val BOARD_SIZE = 3
const val FIRST_PLAYER_SYMBOL = "X"
const val SECOND_PLAYER_SYMBOL = "O"
const val NO_FILL = "_"
const val SEPARATOR = "---------"

enum class TicTacToeStatus(val description: String) {
    IMPOSSIBLE("Impossible"),
    NOT_FINISHED("Game not finished"),
    X_WINS("$FIRST_PLAYER_SYMBOL wins"),
    O_WINS("$SECOND_PLAYER_SYMBOL wins"),
    DRAW("Draw"),
}

class TicTacToe {
    private val gameBoard = Array(BOARD_SIZE) { Array(BOARD_SIZE) { NO_FILL } }
    private var nbCellsFilled = 0
    private var playerOneTurn = true

    var gameStatus = TicTacToeStatus.NOT_FINISHED

    fun printGameBoard() {
        println(SEPARATOR)
        for (row in this.gameBoard) {
            println("| ${row.joinToString(" ")} |")
        }
        println(SEPARATOR)
    }

    private fun updateGameStatus() {
        val isFirstPlayerWinner = isWinner(FIRST_PLAYER_SYMBOL)
        val isSecondPlayerWinner = isWinner(SECOND_PLAYER_SYMBOL)

        this.gameStatus =
            if (!isPossible() || (isFirstPlayerWinner && isSecondPlayerWinner)) {
                TicTacToeStatus.IMPOSSIBLE
            } else if (isFirstPlayerWinner || isSecondPlayerWinner) {
                if (isFirstPlayerWinner) TicTacToeStatus.X_WINS else TicTacToeStatus.O_WINS
            } else if (this.nbCellsFilled != BOARD_SIZE * BOARD_SIZE) {
                TicTacToeStatus.NOT_FINISHED
            } else {
                TicTacToeStatus.DRAW
            }
    }

    private fun isPossible(): Boolean {
        val firstPlayerCount = countSymbol(FIRST_PLAYER_SYMBOL)
        val secondPlayerCount = countSymbol(SECOND_PLAYER_SYMBOL)

        val diffFirstSecond = firstPlayerCount - secondPlayerCount
        val diffSecondFirst = secondPlayerCount - firstPlayerCount

        if (diffFirstSecond >= 2 || diffSecondFirst >= 2) return false

        return true
    }

    private fun countSymbol(symbolToCount: String): Int {
        var count = 0

        for (row in this.gameBoard) {
            for (elem in row) {
                if (elem == symbolToCount) count++
            }
        }

        return count
    }

    private fun isWinner(winnerToCheck: String): Boolean {
        return isWinningRows(winnerToCheck)
                || isWinningColumns(winnerToCheck)
                || isWinningDiagonals(winnerToCheck)
    }

    private fun isWinningRows(winnerToCheck: String): Boolean {
        for (row in this.gameBoard) {
            if (row.joinToString("") == winnerToCheck.repeat(BOARD_SIZE)) {
                return true
            }
        }
        return false
    }

    private fun isWinningColumns(winnerToCheck: String): Boolean {
        for (column in 0 until BOARD_SIZE) {
            var columnLine = ""
            for (row in 0 until BOARD_SIZE) {
                columnLine += this.gameBoard[row][column]
            }
            if (columnLine == winnerToCheck.repeat(BOARD_SIZE)) {
                return true
            }
        }
        return false
    }

    private fun isWinningDiagonals(winnerToCheck: String): Boolean {
        var rightDiagonal = ""
        for (rc in 0 until BOARD_SIZE) {
            rightDiagonal += this.gameBoard[rc][rc]
        }

        if (rightDiagonal == winnerToCheck.repeat(BOARD_SIZE)) return true

        var leftDiagonal = ""
        var row = 0
        for (column in BOARD_SIZE - 1 downTo 0) {
            leftDiagonal += this.gameBoard[row++][column]
        }

        if (leftDiagonal == winnerToCheck.repeat(BOARD_SIZE)) return true

        return false
    }

    private fun isCaseFree(row: Int, column: Int): Boolean {
        return this.gameBoard[row][column] == NO_FILL
    }

    fun playMove(row: Int, column: Int): Boolean {
        if (row !in 0 until BOARD_SIZE || column !in 0 until BOARD_SIZE) {
            println("Coordinates should be from 1 to 3!")
            return false
        }

        if (!isCaseFree(row, column)) {
            println("This cell is occupied! Choose another one!")
            return false
        }

        this.gameBoard[row][column] =
            if (this.playerOneTurn) FIRST_PLAYER_SYMBOL else SECOND_PLAYER_SYMBOL
        this.nbCellsFilled++

        this.playerOneTurn = !this.playerOneTurn

        updateGameStatus()

        return true
    }
}