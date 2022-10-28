fun main() {
    val ticTacToeGame = TicTacToe()
    ticTacToeGame.printGameBoard()

    while (ticTacToeGame.gameStatus == TicTacToeStatus.NOT_FINISHED) {
        var validInput = false
        while (!validInput) {
            try {
                val (row, column) = readln().split(" ")
                validInput = ticTacToeGame.playMove(
                    row.toInt() - 1,
                    column.toInt() - 1
                )
            } catch (e: Exception) {
                println("You should enter numbers!")
            }
        }
        ticTacToeGame.printGameBoard()

        val gameStatusDescription = ticTacToeGame.gameStatus.description
        when (ticTacToeGame.gameStatus) {
            TicTacToeStatus.IMPOSSIBLE -> println(gameStatusDescription)
            TicTacToeStatus.X_WINS -> println(gameStatusDescription)
            TicTacToeStatus.O_WINS -> println(gameStatusDescription)
            TicTacToeStatus.DRAW -> println(gameStatusDescription)
            else -> continue
        }
    }
}

