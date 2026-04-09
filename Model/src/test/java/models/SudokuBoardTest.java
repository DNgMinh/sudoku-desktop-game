package models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import sudoku.model.exceptions.FillingBoardSudokuException;
import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.models.SudokuField;
import sudoku.model.solver.BacktrackingSudokuSolver;

public class SudokuBoardTest {

    @Test
    void testFillBoardCorrectness() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        try {
            sudokuBoard.solveGame();
        } catch (FillingBoardSudokuException e) {
            e.printStackTrace();
            fail("solveGame() threw an InvalidSudokuException");
        }
        assertTrue(sudokuBoard.isValidSudoku());
        // printBoard(sudokuBoard);
    }

    @RepeatedTest(10)
    void testFillBoardSubsequentCalls() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());

        try {
            sudokuBoard1.solveGame();
            sudokuBoard2.solveGame();
        } catch (FillingBoardSudokuException e) {
            e.printStackTrace();
            fail("solveGame() threw an InvalidSudokuException");
        }

        assertTrue(sudokuBoard1.isValidSudoku());
        assertTrue(sudokuBoard2.isValidSudoku());

        assertFalse(checkSameLayout(sudokuBoard1, sudokuBoard2));
    }

    private boolean checkSameLayout(SudokuBoard board1, SudokuBoard board2) {
        for (int y = 0; y < SudokuBoard.BOARD_SIZE; y++) {
            for (int x = 0; x < SudokuBoard.BOARD_SIZE; x++) {
                SudokuField field1 = board1.getField(x, y);
                SudokuField field2 = board2.getField(x, y);
                if (field1.getValue() != field2.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @ParameterizedTest
    @MethodSource("provideInitiallyCorrectBoards")
    void testFillBoardWithInitiallyCorrectBoards(int[][] initialBoard) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        for (int y = 0; y < initialBoard.length; y++) {
            for (int x = 0; x < initialBoard[y].length; x++) {
                sudokuBoard.setField(x, y, initialBoard[y][x]);
            }
        }
        assertTrue(sudokuBoard.isValidSudoku());
    }

    @ParameterizedTest
    @MethodSource("provideInitiallyWrongBoards")
    void testFillBoardWithInitiallyWrongBoards(int[][] initialBoard) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        for (int y = 0; y < initialBoard.length; y++) {
            for (int x = 0; x < initialBoard[y].length; x++) {
                sudokuBoard.setField(x, y, initialBoard[y][x]);
            }
        }

        assertFalse(sudokuBoard.isValidSudoku());
    }

    // Method to provide the test data (initial correct boards)
    private static int[][][] provideInitiallyCorrectBoards() {
        return new int[][][] {
                {
                        { 3, 2, 7, 6, 5, 1, 4, 8, 9 },
                        { 5, 6, 9, 8, 7, 4, 3, 1, 2 },
                        { 1, 4, 8, 2, 3, 9, 6, 7, 5 },
                        { 7, 3, 6, 5, 4, 2, 8, 9, 1 },
                        { 2, 5, 4, 1, 9, 8, 7, 6, 3 },
                        { 9, 8, 1, 3, 6, 7, 2, 5, 4 },
                        { 8, 9, 2, 4, 1, 6, 5, 3, 7 },
                        { 6, 7, 3, 9, 2, 5, 1, 4, 8 },
                        { 4, 1, 5, 7, 8, 3, 9, 2, 6 }
                },

                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 6, 9, 7, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 7, 4, 9, 6, 8, 2, 3, 1 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 6, 9, 8, 7, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 7, 6, 9, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 7, 4, 6, 9, 8, 2, 3, 1 }
                },
                {
                        { 2, 4, 6, 7, 8, 5, 3, 1, 9 },
                        { 1, 3, 5, 4, 6, 9, 7, 8, 2 },
                        { 7, 8, 9, 2, 3, 1, 4, 6, 5 },
                        { 4, 5, 3, 6, 2, 7, 1, 9, 8 },
                        { 8, 1, 2, 9, 5, 4, 6, 3, 7 },
                        { 6, 9, 7, 3, 1, 8, 2, 5, 4 },
                        { 3, 6, 4, 5, 9, 2, 8, 7, 1 },
                        { 5, 7, 1, 8, 4, 3, 9, 2, 6 },
                        { 9, 2, 8, 1, 7, 6, 5, 4, 3 }
                },
        };
    }

    // Method to provide the test data (initial wrong boards)
    private static int[][][] provideInitiallyWrongBoards() {
        return new int[][][] {
                {
                        { 3, 2, 7, 6, 5, 1, 4, 8, 9 },
                        { 5, 6, 9, 8, 7, 4, 3, 1, 2 },
                        { 1, 4, 8, 2, 3, 9, 6, 7, 5 },
                        { 7, 3, 6, 5, 4, 2, 8, 9, 1 },
                        { 2, 5, 4, 1, 8, 8, 7, 6, 3 },
                        { 9, 8, 1, 3, 6, 7, 2, 5, 4 },
                        { 8, 9, 2, 4, 1, 6, 5, 3, 7 },
                        { 6, 7, 3, 9, 2, 5, 1, 4, 8 },
                        { 4, 1, 5, 7, 8, 3, 9, 2, 6 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 9, 7, 8, 9, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 6, 9, 7, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 7, 4, 9, 6, 8, 2, 3, 1 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 6, 9, 8, 7, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 7, 6, 9, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 8, 4, 6, 9, 8, 2, 3, 1 }
                },
                {
                        { 2, 4, 6, 7, 8, 5, 3, 1, 9 },
                        { 1, 2, 5, 4, 6, 9, 7, 8, 2 },
                        { 7, 8, 9, 2, 3, 1, 4, 6, 5 },
                        { 4, 5, 3, 6, 2, 7, 1, 9, 8 },
                        { 8, 1, 2, 9, 5, 4, 6, 3, 7 },
                        { 6, 9, 7, 3, 1, 8, 2, 5, 4 },
                        { 3, 6, 4, 5, 9, 2, 8, 7, 1 },
                        { 5, 7, 1, 8, 4, 3, 9, 5, 6 },
                        { 9, 2, 8, 1, 7, 6, 5, 4, 3 }
                },
        };
    }

    // private static void printBoard(SudokuBoard board) {
    //     for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
    //         for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
    //             System.out.print(board.getField(j, i).getValue() + " ");
    //         }
    //         System.out.println();
    //     }
    // }

    @Test
    void testToString() throws InvalidSudokuException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        sudokuBoard.isValidSudoku();
        assertNotNull(sudokuBoard.toString());
    }

    @Test
    void testEquals_SameObject() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertTrue(sudokuBoard.equals(sudokuBoard));
        assertEquals(sudokuBoard.hashCode(), sudokuBoard.hashCode());
    }

    @Test
    void testEquals_NullObject() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertFalse(sudokuBoard.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertFalse(sudokuBoard.equals(new Object()));
    }

    @Test
    void testEquals_DifferentFields() throws InvalidSudokuException {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();
        assertFalse(sudokuBoard1.equals(sudokuBoard2));
        assertNotEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
    }

    @Test
    void testHashCode() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertNotNull(sudokuBoard.hashCode());
    }

    @Test
    void testClone_same() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard clonedBoard = sudokuBoard.clone();
        assertTrue(sudokuBoard.equals(clonedBoard));
        assertEquals(sudokuBoard.hashCode(), clonedBoard.hashCode());
    }

    @Test
    void testClone_different() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard clonedBoard = sudokuBoard.clone();

        clonedBoard.setField(0, 0, 1);

        assertFalse(sudokuBoard.equals(clonedBoard));
        assertNotEquals(sudokuBoard.hashCode(), clonedBoard.hashCode());
    }

    @Test
    void testCheckEndGameAfterSolvingBoard() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            sudokuBoard.solveGame();
        } catch (FillingBoardSudokuException e) {
            fail("solveGame() should not throw exception for a valid board");
        }

        assertTrue(sudokuBoard.checkEndGame());
    }
}
