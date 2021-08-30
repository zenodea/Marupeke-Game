import MarupekeGame.DiffLevels;
import MarupekeGame.Marupeke;
import MarupekeGame.MarupekeGrid;
import Reason.Reason;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MarupekeGridTest_CW2 {

    private MarupekeGrid grid, complete;

    public void completeGrid()
    {
        complete = new MarupekeGrid(4);
        for (int i = 0; i < complete.getGameBoard().length; i++) {
            for (int j = 0; j < complete.getGameBoard().length; j++)
            {
                complete.setSolid(i,j);
            }
        }
    }

    /*
    unmark tests
            Checking if two grids will be equal or unequal
            depending on the result of the mehtod unmark
    */

    @Test
    public void unmarkEquals()
    {
        grid = new MarupekeGrid(5);
        grid.setX(4,3,true);
        MarupekeGrid temp = new MarupekeGrid((5));
        grid.unmark(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void unmarkUnequals()
    {
        grid = new MarupekeGrid(5);
        grid.setX(4,3,true);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.setX(4,3,true);
        grid.unmark(4,3);
        assertNotEquals(grid.toString(),temp.toString());
    }

    /*
    markX tests
            Checking if two grids will be equal or unequal
            depending on the result of the mehtod markX
    */

    @Test
    public void markXEquals()
    {
        grid = new MarupekeGrid(5);
        grid.markX(4,3);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.setX(4,3,true);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void markXSolid()
    {
        grid = new MarupekeGrid(5);
        grid.setSolid(4,3);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.setSolid(4,3);
        grid.markX(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void markXSubstitute()
    {
        grid = new MarupekeGrid(5);
        grid.markO(4,3);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.markX(4,3);
        grid.markX(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void markXRuleFail()
    {
        grid = new MarupekeGrid(5);
        grid.markX(4,2);
        grid.markX(4,3);
        grid.markX(4,4);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.markX(4,2);
        temp.markX(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    /*
    markO tests
            Checking if two grids will be equal or unequal
            depending on the result of the mehtod markO
    */

    @Test
    public void markOEquals()
    {
        grid = new MarupekeGrid(5);
        grid.markO(4,3);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.setO(4,3,true);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void markOSolid()
    {
        grid = new MarupekeGrid(5);
        grid.setSolid(4,3);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.setSolid(4,3);
        grid.markO(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void markOSubstitute()
    {
        grid = new MarupekeGrid(5);
        grid.markX(4,3);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.markO(4,3);
        grid.markO(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    @Test
    public void markORuleFail()
    {
        grid = new MarupekeGrid(5);
        grid.markO(4,2);
        grid.markO(4,3);
        grid.markO(4,4);
        MarupekeGrid temp = new MarupekeGrid((5));
        temp.markO(4,2);
        temp.markO(4,3);
        assertEquals(grid.toString(),temp.toString());
    }

    /*
    isPuzzleComplete tests
            Checking two cases where the isPuzzleComplete()
            returns true and false
    */

    @Test
    public void isPuzzleCompleteFalse()
    {
        grid = MarupekeGrid.randomPuzzle(5,10,1,0);
        assert grid != null;
        assertFalse(grid.isPuzzleComplete());
    }

    @Test
    public void isPuzzleCompleteTrue()
    {
        completeGrid();
        assertFalse(complete.isPuzzleComplete());
    }

    /*
    isFull tests
            Checking two cases where the isFull()
            returns true and false
    */

    @Test
    public void isFullFalse()
    {
        grid = MarupekeGrid.randomPuzzle(5,10,1,0);
        assert grid != null;
        assertFalse(grid.isFull());
    }

    @Test
    public void isFullTrue()
    {
        completeGrid();
        assertTrue(complete.isFull());
    }

    /*
    isLegalGrid() tests
            Checking to see if the isLegalGrid() works for
            different combinations of consecutive marks
            Horizontal, Vertical and Diagonal
            Also each test uses a different mark (O, X, #)
            The final tests shows a succesfull run of isLegalGrid()
    */

    @Test
    public void isLegalGridFalseXHorizontal()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setX(0,i,true);
        }
        System.out.println(grid);
        assertFalse(grid.isLegalGrid(false));
    }

    @Test
    public void isLegalGridFalseOVertical()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setO(i,0,true);
        }
        System.out.println(grid);
        assertFalse(grid.isLegalGrid(true));
    }
    @Test
    public void isLegalGridFalseSolidDiagonalRight()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setSolid(i,i);
        }
        System.out.println(grid);
        assertFalse(grid.isLegalGrid(false));
    }

    @Test
    public void isLegalGridFalseSolidDiagonalLeft()
    {
        grid = new MarupekeGrid(4);
        grid.setSolid(2,1);
        grid.setSolid(1,2);
        grid.setSolid(0,3);
        System.out.println(grid);
        assertFalse(grid.isLegalGrid(false));
    }

    @Test
    public void isLegalGridTrue()
    {
        grid = MarupekeGrid.randomPuzzle(4,3,3,1);
        System.out.println(grid);
        assertTrue(grid.isLegalGrid(false));
    }

    /*
    illegalitiesInGrid() tests
        Checking to see if the message of the method illegalitiesInGrid()
        are correct for all possible combinations:
        Marks: X, O and _
        Direction: Vertical, Horizontal and Diagonal
        Indexes
    */

    @Test
    public void illegalitiesInGridNotEqual()
    {
        List<Reason> firstGrid, secondGrid;
        grid = MarupekeGrid.randomPuzzle(4,3,3,1);
        assert grid != null;
        firstGrid = grid.illegalitiesInGrid("Diagonal",3,2,'#');
        secondGrid = grid.illegalitiesInGrid("Diagonal",3,2,'X');
        assertNotEquals(firstGrid,secondGrid);
    }

    @Test
    public void illegalitiesInGridEqual()
    {
        List<Reason> firstGrid, secondGrid;
        grid = MarupekeGrid.randomPuzzle(4,3,3,1);
        assert grid != null;
        firstGrid = grid.illegalitiesInGrid("Diagonal",3,2,'#');
        secondGrid = grid.illegalitiesInGrid("Diagonal",3,2,'#');
        assertEquals(firstGrid.toString() ,secondGrid.toString());
    }

    @Test
    public void illegalitiesInGridVerticalErrorO()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setO(i,0,false);
        }
        assertFalse(grid.isLegalGrid(true));
    }

    @Test
    public void illegalitiesInGridHorizontalErrorO()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setO(0,i,false);
        }
        assertFalse(grid.isLegalGrid(true));
    }
    @Test
    public void illegalitiesInGridDiagonalErrorO()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setO(i,i,false);
        }
        assertFalse(grid.isLegalGrid(true));
    }

    @Test
    public void illegalitiesInGridVerticalErrorX()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setX(i,0,false);
        }
        assertFalse(grid.isLegalGrid(true));
    }

    @Test
    public void illegalitiesInGridHorizontalErrorX()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setX(0,i,false);
        }
        assertFalse(grid.isLegalGrid(true));
    }

    @Test
    public void illegalitiesInGridDiagonalErrorX()
    {
        grid = new MarupekeGrid(4);
        for (int i = 0; i < 3; i++)
        {
            grid.setX(i,i,false);
        }
        assertFalse(grid.isLegalGrid(true));
    }

    @Test
    public void illegalitiesInGridSolidBlockO()
    {
        grid = new MarupekeGrid(4);
        grid.setSolid(0,0);
        grid.markO(0,0);
        assertTrue(grid.isLegalGrid(true));
    }

    @Test
    public void illegalitiesInGridSolidBlockX()
    {
        grid = new MarupekeGrid(4);
        grid.setSolid(0,0);
        grid.markX(0,0);
        assertTrue(grid.isLegalGrid(true));
    }

    @Test
    public void solvePuzzle()
    {
        Marupeke dd = new Marupeke(6, DiffLevels.hard);
    }
}
