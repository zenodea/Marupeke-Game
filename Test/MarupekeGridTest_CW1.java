import MarupekeGame.MarupekeGrid;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MarupekeGridTest_CW1 {

    private MarupekeGrid grid;

    /*
    Constructor tests
                Checking if the boundaries limit work, and if the constructor
                successfully initialises the fields.
     */

    @Test
    public void constructorOverSize()
    {
        grid = new MarupekeGrid(30);
        MarupekeGrid correctGrid = new MarupekeGrid(30);
        assertEquals(grid.toString(),correctGrid.toString());
    }
    @Test
    public void constructorUnderSize()
    {
        grid = new MarupekeGrid(3);
        MarupekeGrid correctGrid = new MarupekeGrid(3);
        assertEquals(grid.toString(),correctGrid.toString());
    }
    @Test
    public void constructorFit()
    {
        grid = new MarupekeGrid(7);
        MarupekeGrid correctGrid = new MarupekeGrid(7);
        assertEquals(grid.toString(),correctGrid.toString());
    }

    // Setting up a 10 x 10 Grid to use in the following Tests

    @Before
    public void setUp()
    {
        grid = new MarupekeGrid(10);
    }

    /*
    limits method tests
           Testing the different boundaries for the width and length of the grid
    */


    /*
    setSolid method basic true and false tests with different inputs (#,X,O)
    Note: Hash = #
    */

    @Test
    public void setSolidTrue()
    {
        assertTrue(grid.setSolid(3, 1));
    }
    @Test
    public void setSolidFalseHash()
    {
        grid.setSolid(3,1);
        assertFalse(grid.setSolid(3, 1));
    }
    @Test
    public void setSolidFalseX()
    {
        grid.setX(3,1,false);
        assertFalse(grid.setSolid(3, 1));
    }
    @Test
    public void setSolidFalseO()
    {
        grid.setO(3,1,false);
        assertFalse(grid.setSolid(3, 1));
    }

    /*
    setSolid Corner, Edge, Filled cases Tests
    */

    @Test
    public void setSolidCorners()
    {
        grid.setSolid(0,0);
        grid.setSolid(0,9);
        grid.setSolid(9,0);
        grid.setSolid(9,9);
        assertEquals('#', grid.returnChar(0,0));
        assertEquals('#', grid.returnChar(0,9));
        assertEquals('#', grid.returnChar(9,0));
        assertEquals('#', grid.returnChar(9,9));
        System.out.println(grid);
    }
    @Test
    public void setSolidEdges()
    {
        for (int i = 0; i < 10; i++)
        {
            grid.setSolid(i,0);
            assertEquals('#', grid.returnChar(i,0));
            grid.setSolid(i,9);
            assertEquals('#', grid.returnChar(i,9));
            grid.setSolid(0,i);
            assertEquals('#', grid.returnChar(0,i));
            grid.setSolid(9,i);
            assertEquals('#', grid.returnChar(9,i));
        }
        System.out.println(grid);
    }
    @Test
    public void setSolidFilled()
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                grid.setSolid(i, j);
                assertEquals('#', grid.returnChar(i, j));
            }
        }
        System.out.println(grid);
    }

    /*
    setX test while in the Initial Phase (canEdit = false)
         True and False tests with all possible grid states (#,X,O)
    */

    @Test
    public void setXTrueInitialPhase()
    {
        assertTrue(grid.setX(5, 5,false ));
    }
    @Test
    public void setXFalseInitialPhaseHash()
    {
        grid.setSolid(5,5);
        assertFalse(grid.setX(5, 5,false ));
    }
    @Test
    public void setXFalseInitialPhaseX()
    {
        grid.setX(5,5, false);
        assertFalse(grid.setX(5, 5,false ));
    }
    @Test
    public void setXFalseInitialPhaseO()
    {
        grid.setO(5,5,false);
        assertFalse(grid.setX(5, 5,false ));
    }

    /*
    setX test while in the Player Phase (canEdit = true)
         True and False tests with all possible grid states (#,X,O)
    */

    @Test
    public void setXTruePlayerPhaseX()
    {
        grid.setX(5,5,true);
        assertTrue(grid.setX(5, 5,true ));
    }
    @Test
    public void setXTruePlayerPhaseO()
    {
        grid.setO(5,5,true);
        assertTrue(grid.setX(5, 5,true ));
    }
    @Test
    public void setXFalsePlayerPhaseHash()
    {
        grid.setSolid(5,5);
        assertFalse(grid.setX(5, 5,true ));
    }

    /*
    setX Corner, Edge, Filled cases Tests
    */

    @Test
    public void setXCorners()
    {
        grid.setX(9,0,false);
        grid.setX(9,9,false);
        grid.setX(0,0,false);
        grid.setX(0,9,false);
        assertEquals('X', grid.returnChar(9,0));
        assertEquals('X', grid.returnChar(9,9));
        assertEquals('X', grid.returnChar(0,0));
        assertEquals('X', grid.returnChar(0,9));
        System.out.println(grid);
    }
    @Test
    public void setXEdges()
    {
        for (int i = 0; i < 10; i++)
        {
            grid.setX(i,0, false);
            assertEquals('X', grid.returnChar(i,0));
            grid.setX(i,9,false);
            assertEquals('X', grid.returnChar(i,9));
            grid.setX(0,i,false);
            assertEquals('X', grid.returnChar(0,i));
            grid.setX(9,i,false);
            assertEquals('X', grid.returnChar(9,i));
        }
        System.out.println(grid);
    }
    @Test
    public void setXFilled()
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                grid.setX(i, j, false);
                assertEquals('X', grid.returnChar(i, j));
            }
        }
        System.out.println(grid);
    }

    /*
    setO test while in the Initial Phase (canEdit = false)
         True and False tests with all possible grid states (#,X,O)
    */

    @Test
    public void setOTrueInitialPhase()
    {
        assertTrue(grid.setO(5, 5,false ));
    }
    @Test
    public void setOFalseInitialPhaseHash()
    {
        grid.setSolid(5,5);
        assertFalse(grid.setO(5, 5,false ));
    }
    @Test
    public void setOFalseInitialPhaseX()
    {
        grid.setX(5,5, false);
        assertFalse(grid.setO(5, 5,false ));
    }
    @Test
    public void setOFalseInitialPhaseO()
    {
        grid.setO(5,5,false);
        assertFalse(grid.setO(5, 5,false ));
    }

    /*
    setO test while in the Player Phase (canEdit = true)
         True and False tests with all possible grid states (#,X,O)
    */

    @Test
    public void setOTruePlayerPhaseX()
    {
        grid.setX(5,5,true);
        assertTrue(grid.setO(5, 5,true ));
    }
    @Test
    public void setOTruePlayerPhaseO()
    {
        grid.setO(5,5,true);
        assertTrue(grid.setO(5, 5,true ));
    }
    @Test
    public void setOFalsePlayerPhaseHash()
    {
        grid.setSolid(5,5);
        assertFalse(grid.setO(5, 5,true ));
    }

    /*
    setO Corner, Edge, Filled cases Tests
    */

    @Test
    public void setOCorners()
    {
        grid.setO(9,0,false);
        grid.setO(9,9,false);
        grid.setO(0,0,false);
        grid.setO(0,9,false);
        assertEquals('O', grid.returnChar(9,0));
        assertEquals('O', grid.returnChar(9,9));
        assertEquals('O', grid.returnChar(0,0));
        assertEquals('O', grid.returnChar(0,9));
        System.out.println(grid);
    }
    @Test
    public void setOEdges()
    {
        for (int i = 0; i < 10; i++)
        {
            grid.setO(i,0, false);
            assertEquals('O', grid.returnChar(i,0));
            grid.setO(i,9,false);
            assertEquals('O', grid.returnChar(i,9));
            grid.setO(0,i,false);
            assertEquals('O', grid.returnChar(0,i));
            grid.setO(9,i,false);
            assertEquals('O', grid.returnChar(9,i));
        }
        System.out.println(grid);
    }
    @Test
    public void setOFilled()
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                grid.setO(i, j, false);
                assertEquals('O', grid.returnChar(i, j));
            }
        }
        System.out.println(grid);
    }

    /*
    randomPuzzle Tests
    The Majority of tests for randomPuzzle() are present in the parameterised
    test class called MarupekeGridParamTest in the Test Directory
     */

    @Test
    public void toStringNotNullTest()
    {
        MarupekeGrid randPuzzle = new MarupekeGrid (10);
        assertNotNull(randPuzzle.toString());
    }
    @Test
    public void toStringEmptyMin()
    {
        MarupekeGrid emptyGrid = new MarupekeGrid(3);
        assertEquals("____\r\n____\r\n____\r\n____\r\n", emptyGrid.toString());
    }
    @Test
    public void toStringEmptyMax()
    {
        MarupekeGrid emptyGrid = new MarupekeGrid(10);
        assertEquals("__________\r\n__________\r\n__________\r\n__________\r\n__________\r\n__________" +
                        "\r\n__________\r\n__________\r\n__________\r\n__________\r\n",
                emptyGrid.toString());
    }
    @Test
    public void toStringCornerEdgeFill()
    {
        MarupekeGrid toStringTest = new MarupekeGrid(7);
        for (int i = 0; i < 7; i++)
        {
            toStringTest.setSolid(0,i);
            toStringTest.setSolid(6,i);
            toStringTest.setSolid(i,0);
            toStringTest.setSolid(i,6);
        }
        assertEquals("#######\r\n#_____#\r\n" +
                        "#_____#\r\n#_____#\r\n#_____#\r\n" +
                        "#_____#\r\n#######\r\n",
                toStringTest.toString());
    }
    @Test
    public void toStringCornerEdgeX()
    {
        MarupekeGrid toStringTest = new MarupekeGrid(3);
        for (int i = 0; i < 3; i++)
        {
            toStringTest.setX(0,i,false);
            toStringTest.setX(2,i,false);
            toStringTest.setX(i,0,false);
            toStringTest.setX(i,2,false);
        }
        assertEquals("XXX_\r\nX_X_\r\nXXX_\r\n____\r\n", toStringTest.toString());
    }
    @Test
    public void toStringCornerEdgeO()
    {
        MarupekeGrid toStringTest = new MarupekeGrid(5);
        for (int i = 0; i < 5; i++)
        {
            toStringTest.setO(0,i,false);
            toStringTest.setO(4,i,false);
            toStringTest.setO(i,0,false);
            toStringTest.setO(i,4,false);
        }
        assertEquals("OOOOO\r\nO___O\r\nO___O\r\nO___O\r\nOOOOO\r\n", toStringTest.toString());
    }
}
