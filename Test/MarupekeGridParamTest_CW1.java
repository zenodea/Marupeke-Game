import MarupekeGame.MarupekeGrid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MarupekeGridParamTest_CW1
{
    private final int  size, numFill, numX, numO;

    public MarupekeGridParamTest_CW1(int size, int numFill, int numX, int numO)
    {
        this.size = size;
        this.numFill = numFill;
        this.numX = numX;
        this.numO = numO;
    }
    @Parameterized.Parameters(name = "{index}: Marupeke Grid: size {0}, fill {1}, X {2}, O {3}")
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]
                {
                        // Normal Test
                        {10,4,3,2},
                        // Size boundary test
                        {10,9,4,10},
                        // Size lower boundary, maximum items split
                        {3,3,3,3},
                        // Size lower boundary, maximum items #
                        {3,9,0,0},
                        // Size lower boundary, maximum items X
                        {3,0,9,0},
                        // Size lower boundary, maximum items O
                        {3,0,0,9},
                });
    }
    /*
    randomPuzzleTest test the randomPuzzle method a number of times,
    everytime with different values (values found in the public static Collection<Object[]> data())
    and checks if the result is not null.
    */
    @Test
    public void randomPuzzleTest()
    {
        MarupekeGrid randPuzzle;
        randPuzzle = MarupekeGrid.randomPuzzle(size,numFill,numX,numO);
        System.out.println(randPuzzle);
        assertNotNull(randPuzzle);
    }

}
