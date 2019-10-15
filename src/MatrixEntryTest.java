import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;



public class MatrixEntryTest {

    private MatrixEntry matrix;
    private Random rand = new Random();
    private final int ROW = 10;
    private final int COL = 10;
    private final int DATA = 4;
    private MatrixEntry rowReference;
    private MatrixEntry colReference;

    @Before
    public void setUp() {
        matrix = new MatrixEntry(ROW, COL, DATA);
    }


    // Tests constructor if they initializes correctly
    @Test
    public void TestInitializer() {
        assertEquals(ROW, matrix.getRow());
        assertEquals(COL, matrix.getColumn());
        assertEquals(DATA, matrix.getData());
    }

    // Testing setter methods
    @Test
    public void TestSetMethods(){
        matrix.setRow(ROW);
        matrix.setColumn(COL);
        matrix.setData(DATA);

        assertEquals(ROW, matrix.getRow());
        assertEquals(COL, matrix.getColumn());
        assertEquals(DATA, matrix.getData());

    }

    // Testing test next column
    @Test
    public void TestNextColumn(){
        matrix.setNextCol(new MatrixEntry(rand.nextInt(ROW),rand.nextInt(COL),rand.nextInt(DATA)));
        assertEquals(matrix.getNextCol(), matrix.getNextCol());
    }

    // Testing test next column
    @Test
    public void TestNextRow(){
        matrix.setNextRow(new MatrixEntry(rand.nextInt(ROW),rand.nextInt(COL),rand.nextInt(DATA)));
        assertEquals(matrix.getNextRow(), matrix.getNextRow());
    }


}

