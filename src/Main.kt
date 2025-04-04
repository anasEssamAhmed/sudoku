fun main() {
    val valid9x9 = arrayOf(
        arrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
        arrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
        arrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
        arrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
        arrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
        arrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
        arrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
        arrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
        arrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )

    val valid4x4 = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(3, 4, 1, 2),
        arrayOf(2, 1, 4, 3),
        arrayOf(4, 3, 2, 1)
    )

    val rowError = arrayOf(
        arrayOf(5, 3, 4, 6, 7, 8, 9, 1, 1), // Duplicate 1
        arrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
        arrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
        arrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
        arrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
        arrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
        arrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
        arrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
        arrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )

    val columnError = arrayOf(
        arrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
        arrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
        arrayOf(5, 9, 8, 3, 4, 2, 5, 6, 7), // Duplicate 5 in column
        arrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
        arrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
        arrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
        arrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
        arrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
        arrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )

    val subgridError = arrayOf(
        arrayOf(1, 1, 3, 4),
        arrayOf(2, 4, 1, 3),
        arrayOf(3, 2, 4, 1),
        arrayOf(4, 3, 2, 1)
    )

    val sizeRowError = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(2, 3, 4, 1),
        arrayOf(4, 1, 2, 3)
    )

    val sizeColumnError = arrayOf(
        arrayOf(1, 2, 3),
        arrayOf(2, 3, 4),
        arrayOf(3, 4, 3),
        arrayOf(4, 1, 1)
    )

    val rangeError = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(5, 3, 4, 1),
        arrayOf(4, 1, 2, 3),
        arrayOf(3, 4, 1, 2)
    )

    test(
        testCase = "Giving a Full Grid valid, should return true",
        board = valid9x9,
        gameType =  9,
        result = ::sudokuChecker,
        correctResult = true
    )
    test(
        testCase = "Giving a Full Grid valid, should return true",
        board =  valid4x4,
        gameType = 4,
        result = ::sudokuChecker,
        correctResult = true
    )
    test(
        testCase = "Giving a grid with Invalid row with number is repeated, should return false",
        board =  rowError,
        gameType = 9,
        result = ::sudokuChecker,
        correctResult = false)
    test(
        testCase = "Giving a grid with Invalid Column with number is repeated, should return false",
        board = columnError,
        gameType =  9,
        result = ::sudokuChecker,
        correctResult = false
    )
    test(
        testCase = "Giving a grid with Invalid Subgrid with number is repeated, should return false",
        board = subgridError,
        gameType = 4,
        result = ::sudokuChecker,
        correctResult = false
    )
    test(
        testCase = "Giving a grid with a missing row, should return false",
        board = sizeRowError,
        gameType = 4,
        result = ::sudokuChecker,
        correctResult = false
    )
    test(
        testCase = "Giving a grid with Invalid Size as there is a missing Column, should return false",
        board = sizeColumnError,
        gameType = 4,
        result = ::sudokuChecker,
        correctResult = false
    )
    test(
        testCase = "Giving a grid with Invalid number range as 5 was added in 4*4 game, should return false",
        board =  rangeError,
        gameType = 4,
        result = ::sudokuChecker,
        correctResult =  false
    )
}


fun test(
    testCase: String,
    board: Array<Array<Int>>,
    gameType: Int,
    result: (Array<Array<Int>>, Int) -> Boolean,
    correctResult: Boolean
) {
    val actual = result(board, gameType)
    println("Test case: $testCase Expected: $correctResult, Got: $actual Test ${if (actual == correctResult) "PASSED" else "FAILED"}")
}

fun sudokuChecker(sudoku: Array<Array<Int>>, gameType: Int): Boolean {
    return checkRow(sudoku) && checkColumn(sudoku, gameType) && sudokuSafetyVerification(sudoku, gameType)
}

fun checkRow(sudokuGrid: Array<Array<Int>>): Boolean {
    var condition = true
    loop@ for (currentRow in sudokuGrid) {
        var newRow = mutableListOf<Int>() // this list will use to store number
        for (rowNumber in currentRow) {
            newRow.add(rowNumber)
            var count = 0
            for (number in newRow) {
                if (rowNumber == number) {
                    count += 1
                }
                if (count == 2) {
                    condition = false
                    break@loop
                }
            }
        }
    }
    return condition
}

fun checkColumn(sudoku: Array<Array<Int>>, gameType: Int): Boolean {
    var condition = true
    var numberUsingInLoop = 0
    when (gameType) {
        9 -> numberUsingInLoop = 8
        4 -> numberUsingInLoop = 3
    }
    loop@ for (i in 0..numberUsingInLoop) {
        var newArray = mutableListOf<Int>()
        for (array in sudoku) {
            newArray.add(array[i])
            var count = 0
            for (number in newArray) {
                if (array[i] == number) {
                    count += 1
                }
                if (count == 2) {
                    condition = false
                    break@loop
                }
            }
        }
    }
    return condition
}

fun sudokuSafetyVerification(sudoku: Array<Array<Int>>, gameType: Int): Boolean {
    when (gameType) {
        9 -> {
            if (sudoku.size != 9) return false
            for (array in sudoku) {
                if (array.size != 9) return false
                for (row in array) {
                    if (row !in 1..9) return false
                }
            }
        }

        4 -> {
            if (sudoku.size != 4) return false
            for (array in sudoku) {
                if (array.size != 4) return false
                for (row in array) {
                    if (row !in 1..4) return false
                }
            }
        }

        else -> return false
    }
    return true
}
