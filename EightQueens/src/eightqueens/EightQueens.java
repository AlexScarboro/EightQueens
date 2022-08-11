package eightqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

/**
 * @author alexs
 */
public class EightQueens {
    
    private int row;
    private int column;
    
    public EightQueens(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    //method to move the Queens down in their column
    public void move() { 
        row++;
    }
    
    /*function for checking conflict with other queens. Get the row and column from
    the Queen that youre passing in */
    public boolean checkConflict(EightQueens q){
        
        /*Check rows and columns. If the row youre in matches the row you pass in,
        or the same for the column */
        if (row == q.getRow() || column == q.getColumn()) {
            return true; //true, there is a conflict
        } else if (Math.abs(column - q.getColumn()) == Math.abs(row - q.getRow())) {
            /*Check diagonals. The abs value lets you check the diags to the left and right
            of the Queen. */
            return true;
        }
        return false;
            

        
        
    }
    
    //getter for the row
    public int getRow() {
        return row;
    }

    //getter for the column
    public int getColumn() {
        return column;
    }
    
}           
 