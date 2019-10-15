public class MatrixEntry {

    private int row;
    private int col;
    private int data;
    private MatrixEntry rowReference;
    private MatrixEntry colReference;


    public MatrixEntry(int row, int col, int data) {
        this.row = row;
        this.col = col;
        this.data = data;
    } //End Constructor

    public int getColumn(){
        return col;
    } //returns the column index

    public void setColumn(int col){
        this.col = col;
    } //sets the column index to given input

    public int getRow(){
        return row;
    } //Returns the row index

    public void setRow(int row){
        this.row = row;
    } //sets the row index to given input

    public int getData(){ //returns the data of a MatrixEntry
        return data;
    }

    public void setData(int data){
        this.data = data;
    } //Changes the data of a matrix entry to the input data

    public MatrixEntry getNextRow(){ //Gets the the next matrix entry of a given column (goes down in the same column to next entry)
        //same column number
        //different row number ++
        return rowReference;
    }

    public void setNextRow(MatrixEntry el){
        rowReference = el;
    } //Sets the next row from a given matrix entry to the input entry

    public MatrixEntry getNextCol(){ //Moves across the row to the next matrix entry
        //Same row
        //different col number ++
        return colReference;
    }

    public void setNextCol(MatrixEntry el){
        colReference = el;
    } //Sets the next column from a given matrix to the input entry
}
