import MarupekeGame.MarupekeGrid;
import MarupekeGame.SquareType;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarupekeTileTest_CW2 {

    private MarupekeGrid grid;

    @Test
    public void makeTrueTest()
    {
        grid = new MarupekeGrid(4);
        boolean[][] allTrue = new boolean[4][4];
        boolean[][] gridTrue = new boolean[4][4];
        for(int i = 0; i < grid.getGameBoard().length; i++)
        {
            for(int j = 0; j < grid.getGameBoard().length; j++)
            {
                gridTrue[i][j] = grid.getGameBoard()[i][j].getEditable();
                allTrue[i][j] = true;
            }
        }
        assertArrayEquals(gridTrue,allTrue);
    }

    @Test
    public void fillTest()
    {
        grid = new MarupekeGrid(4);
        char[][] allEmpty = new char[4][4];
        char[][] gridChar = new char[4][4];
        for(int i = 0; i < grid.getGameBoard().length; i++)
        {
            for(int j = 0; j < grid.getGameBoard().length; j++)
            {
                gridChar[i][j] = grid.getGameBoard()[i][j].getMarkChar();
                allEmpty[i][j] = '_';
            }
        }
        assertArrayEquals(gridChar,allEmpty );
    }

    @Test
    public void getMarcCharTest()
    {
        grid = new MarupekeGrid(4);
        grid.setO(1,1,true);
        grid.setX(1,2,true);
        grid.setSolid(1,3);
        assertEquals(grid.getGameBoard()[1][0].getMarkChar(), '_');
        assertEquals(grid.getGameBoard()[1][1].getMarkChar(), 'O');
        assertEquals(grid.getGameBoard()[1][2].getMarkChar(), 'X');
        assertEquals(grid.getGameBoard()[1][3].getMarkChar(), '#');
    }

    @Test
    public void getMarcEnumTest()
    {
        grid = new MarupekeGrid(4);
        grid.setO(1,1,true);
        grid.setX(1,2,true);
        grid.setSolid(1,3);
        assertEquals(grid.getGameBoard()[1][0].getMarkEnum(), SquareType.EMPTY);
        assertEquals(grid.getGameBoard()[1][1].getMarkEnum(), SquareType.O);
        assertEquals(grid.getGameBoard()[1][2].getMarkEnum(), SquareType.X);
        assertEquals(grid.getGameBoard()[1][3].getMarkEnum(), SquareType.SOLID);
    }

    @Test
    public void setMarkTest()
    {
        grid = new MarupekeGrid(4);
        grid.getGameBoard()[1][1].setMark(SquareType.O);
        grid.getGameBoard()[1][2].setMark(SquareType.X);
        grid.getGameBoard()[1][3].setMark(SquareType.SOLID);
        assertEquals(grid.getGameBoard()[1][1].getMarkChar(), 'O');
        assertEquals(grid.getGameBoard()[1][2].getMarkChar(), 'X');
        assertEquals(grid.getGameBoard()[1][3].getMarkChar(), '#');
    }

    @Test
    public void getEditableTest()
    {
        grid = new MarupekeGrid(4);
        grid.setO(1,2,false);
        assertTrue(grid.getGameBoard()[1][1].getEditable());
        assertFalse(grid.getGameBoard()[1][2].getEditable());
    }

    @Test
    public void setEditableTest()
    {
        grid = new MarupekeGrid(4);
        grid.setO(1,2,false);
        assertTrue(grid.getGameBoard()[1][1].getEditable());
        assertFalse(grid.getGameBoard()[1][2].getEditable());
    }
}
