package eightqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author alexs
 */
public class HillClimbing_RandomRestart {
    
    private static int n = 8;
    private static int currentHeuristic = 0;
    private static int lowerHeuristicNeighbors = 0;
    static int localMinima = 0; /* localMinima achieved when after looking at all moves
    you can make, none give a lower heuristic than the currentHeuristic */
    private static int stateChanges = 0; 
    private static int randomRestarts = 0; 
        
    //Method to create a new random board
    public static EightQueens[] generateBoard() {
        stateChanges++; //increment stateChanges now that I've made a board.
        EightQueens[] startBoard = new EightQueens[n]; //new 1d array for cols 
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            startBoard[i] = new EightQueens(rand.nextInt(n), i); //random coordinates for row and col
        }
        return startBoard;
    }
    
    //Method to print the current state
    private static void printCurrentState(EightQueens[] state) {
        //Creating temporary board from the present board
        int[][] tempBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            /*Get the positions of the Queen from the Present board. Then set those
            positions as 1 in the temp board */
            tempBoard[state[i].getRow()][state[i].getColumn()] = 1;
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Method to calculate the a state's current heuristic
    public static int calculateCurrentHeuristic(EightQueens[] state) { //1d array for cols of Queen
        int currentHeuristic = 0;
        for (int i = 0; i < state.length; i++) { //nested for loop for rows & cols of curr state
            for (int j = 0; j < state.length; j++) {
                if (state[i].checkConflict(state[j])) { //if there is conflict, increase heuristic
                    currentHeuristic++;
                }
            }
        }
        return currentHeuristic;
    }

    //Method to check the neighbors and get the next board with a lower heuristic 
    public static EightQueens[] nextBoard(EightQueens[] currentBoard) { //current board points where the Queens are
        EightQueens[] nextBoard = new EightQueens[n]; //new EightQueens array called nextBoard of size n
        EightQueens[] tempBoard = new EightQueens[n]; //new EightQueens array called tempBoard of size n
        int currentHeuristic = calculateCurrentHeuristic(currentBoard);
        //bestHeuristic is setting the best board equal to the current heuristic
        int bestHeuristic = currentHeuristic; 
        int tempHeuristic;
                
        for (int i = 0; i < n; i++) {
            /*Copy the currentBoard's row and column values into nextBoard,
            and set nextBoard and tempBoard to those values */
            nextBoard[i] = new EightQueens(currentBoard[i].getRow(), currentBoard[i].getColumn());
            tempBoard[i] = nextBoard[i];
        }
        
        //Iterate thru the cols
        for (int i = 0; i < n; i++) {
            /*if i is a Queen on the temporary board, get the previous row and previous
            column from the current board */
            if (i > 0) { 
                tempBoard[i - 1] = new EightQueens(currentBoard[i - 1].getRow(), currentBoard[i - 1].getColumn());
            }
            /*then set the value at position i on the tempBoard to be row 0 and the col
            from the tempBoard */
            tempBoard[i] = new EightQueens(0, tempBoard[i].getColumn());
            
            //Iterate thru the rows
            for (int j = 0; j < n; j++) {
                //Get the heuristic of the tempBoard and set it to tempHeuristic
                tempHeuristic = calculateCurrentHeuristic(tempBoard);
                //Check if tempBoard has a lower heuristic than the bestBoard
                if (tempHeuristic < bestHeuristic) {
                    //set the new bestHeuristic equal to the tempHeuristic
                    bestHeuristic = tempHeuristic;
                    //Copy the temp board as best board
                    for (int k = 0; k < n; k++) {
                        nextBoard[k] = new EightQueens(tempBoard[k].getRow(), tempBoard[k].getColumn());
                    }
                }
                /* If the row of the temp board at position i isn't the previous row,
                then move the Queen on the tempBoard */
                if (tempBoard[i].getRow() != n - 1) {
                    tempBoard[i].move();
                    stateChanges++; //state change because I'm moving Queen
                }
            }
        }
        /* Check whether the currentBoard and the bestBoard found have the same heuristic
        value. Then randomly generate a new board and assign it to the bestBoard.
        bestBoard = bestHeuristic */
        if (bestHeuristic == currentHeuristic) { //you've done as good as you can, have to restart
            randomRestarts++;
            nextBoard = generateBoard();
            currentHeuristic = calculateCurrentHeuristic(nextBoard);
        } else { //else, found a better heuristic
            currentHeuristic = bestHeuristic;
            lowerHeuristicNeighbors++;
        }
        return nextBoard;
    }
    
    public static void main(String[] args) { 
        int presentHeuristic;
        
        //Creating the initial Board
        EightQueens[] presentBoard = generateBoard();
        
        stateChanges++; //state change now that the board has been created
        presentHeuristic = calculateCurrentHeuristic(presentBoard);
        if (presentHeuristic > 0) {
            System.out.println("Current heuristic: " + presentHeuristic); 
        }
       
        System.out.println("Current state: ");
        printCurrentState(presentBoard);
        
        if (presentHeuristic == 0) {
            System.out.println("Solution Found!");
            System.out.println("State changes: " + stateChanges);
            System.out.println("Number of random restarts: " + randomRestarts);
        } 
        
        //test if the present board is the solution 
        while (presentHeuristic != 0) {
            //Get the next board and set the presentBoard to the nextBoard
            presentBoard = nextBoard(presentBoard);
            presentHeuristic = currentHeuristic;
            
            if (presentHeuristic == 0) { //if the presentHeuristic is 0, you found a solution, so print the following information.
                System.out.println("Solution Found!");
                System.out.println("State changes: " + stateChanges);
                System.out.println("Number of random restarts: " + randomRestarts);
            } else {
                System.out.println("Neighbors found with lower heuristic: " + lowerHeuristicNeighbors);
            
                if (lowerHeuristicNeighbors == 0) { //if you reached the local minima, restart the search
                    System.out.println("RESTART");
                } else {
                    System.out.println("Setting new current state");
                }
            }
        }
    }
    
}
