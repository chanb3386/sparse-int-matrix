//Problem A
//For an NxN square matrix with m non-zero elements a sparse matrix would
//require 5*m in memory because you have to store data, row, column, next row, next column.
//A 2D array would have to store N*N elements because the empty entries store zeroes instead of null values.

//Problem B
//A 100,000 by 100,000 matrix would have to store 10 billion entries in a 2D array regardless of M.
//If M = 1,000,000, for a sparse matrix, it would have to store 5 million entries, so therefore it is
//far more efficient for relatively low values of M.
//Once M = 2 billion, they would be equally efficient in data storage space. So for M > 2 billion,
//a 2D array implementation of a matrix would be more efficient in terms of storage.

//Chanx411
//Nguy3817



import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
public class SparseIntMatrix {

    private int numRows;
    private int numCols;
    private File file;    //String with the filename of a file with matrix data
    private int length = 0;
    private MatrixEntry[] row1;  // Array for row storing matrixEntry Objects
    private MatrixEntry[] col1;// Array for column storing MatrixEntry Objects

    public SparseIntMatrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        row1 = new MatrixEntry[numRows];    // Stores MatrixEntry memory for rows
        col1 = new MatrixEntry[numCols];    // Stores MatrixEntry memory for columns

        //Set all matrix values to null
        for (int i = 0; i < row1.length; i++) {
            row1[i] = null;
            length++;
        }
        for (int j = 0; j < col1.length; j++) {
            col1[j] = null;
            length++;
        }
    }

    public SparseIntMatrix(int numRows, int numCols, String inputFile) {
        this.numRows = numRows;
        this.numCols = numCols;
        file = new File(inputFile);       //row,column,data
        row1 = new MatrixEntry[numRows];    // Stores MatrixEntry memory for rows
        col1 = new MatrixEntry[numCols];    // Stores MatrixEntry memory for columns


        //Set all matrix values to null
        for (int i = 0; i < row1.length; i++) {
            row1[i] = null;
        }
        for (int j = 0; j < col1.length; j++) {
            col1[j] = null;
        }

        // Scanner
        Scanner s = new Scanner(inputFile);

        try {
            s = new Scanner(file);
        } catch (FileNotFoundException ex) {

            System.out.println("There was an error opening the file");
        }

        while (s.hasNextLine()) {
            //row, column, data
            //how do i make it so it recognizes the first is row , second is column, third is data
            String q[];
            q = s.nextLine().split(",");

            int r = Integer.parseInt(q[0]);
            int c = Integer.parseInt(q[1]);
            int d = Integer.parseInt(q[2]);

            setElement(r, c, d);
        }


    }

    public int getElement(int row, int col) {
        // InBounds check
        if (row >= row1.length || col >= col1.length || col < 0 || row < 0) {
            return 0;
        } else {
            MatrixEntry rowStart = row1[row];
            MatrixEntry colStart = col1[col];
            // Checks to see if there is an element in the specified location
            if (rowStart == null || colStart == null) {
                return 0;
            } else {
                //sifts through the column for the element
                while (rowStart != null) {
                    if (rowStart.getColumn() == col) {
                        return rowStart.getData();
                    }
                    rowStart = rowStart.getNextCol();
                }
            }
        }
        return 0;
    }

    public boolean setElement(int row, int col, int data) {
        //check in range and return true, otherwise just return false
        if (row < row1.length && col < col1.length && col >= 0 && row >= 0) {
            MatrixEntry e = new MatrixEntry(row, col, data);
            boolean a = placeRow(row, e);
            boolean b = placeCol(row, e);
            return a & b;
        } else {
            return false;
        }
    }

    private boolean placeRow(int row, MatrixEntry entry) {
        boolean check = true;
        MatrixEntry currentRow = row1[row];
        MatrixEntry previousRow = null;

        // Check to see if spot is empty then assign it to the new entry
        if (row1[row] == null) {
            row1[row] = entry;
            return true;
        } else {
            MatrixEntry after = currentRow.getNextCol();
            while (check) {
                // END case
                if (currentRow.getNextCol() == null) {
                    if (entry.getColumn() > currentRow.getColumn()) {
                        currentRow.setNextCol(entry);
                        return true;
                    } else if (entry.getColumn() < currentRow.getColumn()) {
                        previousRow.setNextCol(entry);
                        entry.setNextCol(currentRow);
                        return true;
                    }
                    // Between two nodes before the current case
                } else if (previousRow != null && currentRow.getColumn() > entry.getColumn() && previousRow.getColumn() < entry.getColumn()) {
                    previousRow.setNextCol(entry);
                    entry.setNextCol(currentRow);
                    return true;
                    // Between two nodes after the current and before the node after case
                } else if (entry.getColumn() > currentRow.getColumn() && entry.getColumn() < after.getColumn()) {
                    currentRow.setNextCol(entry);
                    entry.setNextCol(after);
                    return true;
                    // overriding data case
                } else if (currentRow.getColumn() == entry.getColumn()) {
                    currentRow.setData(entry.getData());
                    return true;
                    // Beginning of non empty matrix entry case
                } else if (entry.getColumn() < currentRow.getColumn() && previousRow == null) {
                    row1[row] = entry;
                    entry.setNextCol(currentRow);
                    return true;
                } else if (previousRow != null && (previousRow.getColumn() == entry.getColumn() || after.getColumn() == entry.getColumn())) {
                    if (previousRow.getColumn() == entry.getColumn()) {
                        previousRow.setData(entry.getData());
                        return true;
                    } else if (entry.getColumn() == after.getColumn()) {
                        after.setData(entry.getData());
                        return true;
                    }
                }
                // Incrementing
                previousRow = currentRow;
                currentRow = currentRow.getNextCol();
                after = currentRow.getNextCol();
            }
        }
        return false;
    }

    private boolean placeCol(int col, MatrixEntry entry) {
        boolean check = true;
        MatrixEntry currentCol = col1[col];
        MatrixEntry previousCol = null;
        // Check to see if spot is empty then assign it to the new entry
        if (col1[col] == null) {
            col1[col] = entry;
            return true;
        } else {
            MatrixEntry after = currentCol.getNextRow();
            while (check) {
                // END case
                if (currentCol == null) {
                    if (entry.getRow() > currentCol.getRow()) {
                        currentCol.setNextRow(entry);
                        return true;
                    } else if (entry.getRow() < currentCol.getRow()) {
                        previousCol.setNextRow(entry);
                        entry.setNextRow(currentCol);
                        return true;
                    }
                    // Between two nodes before the current case
                } else if (previousCol != null && currentCol.getRow() > entry.getRow() && previousCol.getRow() < entry.getRow()) {
                    previousCol.setNextRow(entry);
                    entry.setNextRow(currentCol);
                    return true;
                    // Between two nodes after the current and before the node after case
                } else if (entry.getRow() > currentCol.getRow() && entry.getRow() < after.getRow()) {
                    currentCol.setNextRow(entry);
                    entry.setNextRow(after);
                    return true;
                    // overriding data case
                } else if (currentCol.getRow() == entry.getRow()) {
                    currentCol.setData(entry.getData());
                    return true;
                    // Beginning of non empty matrix entry case
                } else if (entry.getRow() < currentCol.getRow() && previousCol == null) {
                    col1[col] = entry;
                    entry.setNextRow(currentCol);
                    return true;
                } else if (previousCol != null && (previousCol.getRow() == entry.getRow() || after.getRow() == entry.getRow())) {
                    if (previousCol.getRow() == entry.getRow()) {
                        previousCol.setData(entry.getData());
                        return true;
                    } else if (entry.getRow() == after.getRow()) {
                        after.setData(entry.getData());
                        return true;
                    }
                }
                // Incrementing
                previousCol = currentCol;
                currentCol = currentCol.getNextRow();
                if (after == null) {
                    after = currentCol;
                } else {

                    after = currentCol.getNextRow();
                }

            }
        }
        return false;
    }

    public boolean removeElement(int row, int col, int data) {
        boolean check = true;
        // Check inbounds
        if (row >= row1.length || col >= col1.length || col < 0 || row < 0) {
            return false;
        } else {
            MatrixEntry currentRow = row1[row];
            MatrixEntry currentCol = col1[col];
            MatrixEntry after = currentRow.getNextCol();
            MatrixEntry previous = null;
            while (check) {
                // If there is no element to remove return false
                if (currentRow == null && currentCol == null) {
                    return false;
                } else {
                    // Checks to see if the element wanting to be removed is found
                    if (currentRow.getColumn() == col && data == currentRow.getData()) {
                        previous.setNextCol(after);
                        return true;
                    } else if (previous == null && currentRow.getColumn() == col) {
                        currentRow = after;
                        after = null;
                        return true;
                    }
                }
                // Incrementing
                previous = currentRow;
                currentRow = currentRow.getNextCol();
                after = currentRow.getNextCol();
            }
            return false;
        }
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public boolean plus(SparseIntMatrix otherMat) {

        // Check to see if matrices are identical size
        if (otherMat.getNumRows() == this.getNumRows() && otherMat.getNumCols() == this.getNumCols()) {
            // Sifts through both rows and column of both and find data in both matrices
            for (int i = 1; i < getNumRows(); i++) {
                for (int j = 1; j < getNumCols(); j++) {
                    int data = this.getElement(i, j) + otherMat.getElement(i, j);
                    // Checks if it is null
                    if (data != 0) {
                        this.setElement(i, j, data);
                    }
                }
            }

        } else {
            return false;
        }

        return true;
    }

    public boolean minus(SparseIntMatrix otherMat) {

        // Check to see if matrices are identical size
        if (otherMat.getNumRows() == this.getNumRows() && otherMat.getNumCols() == this.getNumCols()) {
            // Sifts through both rows and column of both and find data in both matrices
            for (int i = 0; i < getNumRows(); i++) {
                for (int j = 0; j < getNumCols(); j++) {
                    int data = this.getElement(i, j) - otherMat.getElement(i, j);
                    // Checks if it is false
                    if (data != 0) {
                        this.setElement(i, j, data);
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SparseIntMatrix mat = new SparseIntMatrix(800, 800, "matrix1_data.txt");
        //System.out.println(mat.getElement(143,657));
        MatrixViewer.show(mat);
        SparseIntMatrix mat2 = new SparseIntMatrix(800, 800, "matrix2_data.txt");
        SparseIntMatrix mat3 = new SparseIntMatrix(800, 800, "matrix2_noise.txt");
        mat2.minus(mat3);
        //MatrixViewer.show(mat2);


    }

}


