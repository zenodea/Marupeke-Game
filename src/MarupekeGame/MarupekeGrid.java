package MarupekeGame;
import java.lang.*;
import java.util.*;
import Reason.*;



public class MarupekeGrid {
    final int size;
    private final MarupekeTile[][] gameBoard;
    private List<Reason> illegalitiesInGrid;
    public MarupekeGrid(int size)
    {
        if (size < 4) size = 4;
        if (size > 10) size = 10;
        this.size = size;
        gameBoard = new MarupekeTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = new MarupekeTile();
            }
        }
    }





    public MarupekeTile[][] getGameBoard()
    {
        return gameBoard;
    }
    /**
     * setSolid will send the specified information to setGrid to make
     * the specified square as SquareType.FILLED ('#')
     *
     * @param column Column of the MarupekeGrid
     * @param row Row of the MarupekeGrid
     *
     * @return WIll return true if the operation was successful, false otherwise
     */
    public boolean setSolid(int column, int row) {return setGrid(column,row,false, SquareType.SOLID);}

    /**
     * setX will send the specified information to setGrid to make
     * the specified square as SquareType.X ('X')
     *
     * @param column Column of the MarupekeGrid
     * @param row Row of the MarupekeGrid
     * @param canEdit If the square will be editable afterwards
     *
     * @return WIll return true if the operation was successful, false otherwise
     */
    public boolean setX(int column, int row, boolean canEdit) {return setGrid(column,row,canEdit, SquareType.X);}

    /**
     * setO will send the specified information to setGrid to make
     * the specified square as SquareType.O ('O')
     *
     * @param column Column of the MarupekeGrid
     * @param row Row of the MarupekeGrid
     * @param canEdit If the square will be editable afterwards
     *
     * @return WIll return true if the operation was successful, false otherwise
     */
    public boolean setO(int column, int row, boolean canEdit) {return setGrid(column,row,canEdit, SquareType.O);}

    /**
     * setGrid will set the specified grid (x and y) as the SquareType (block) it
     * received. The canEdit boolean will decide if the grid set will later on
     * be editable again.
     *
     * @param column column of the MarupekeGrid
     * @param row row of the MarupekeGrid
     * @param canEdit if true, grid x y editable again, if false, grid x y can't be changed
     * @param block SquareType that will replace the SquareType in field gameBoard.grid
     *
     * @return Will return true if the operation was successful, false otherwise
     */
    public boolean setGrid(int column, int row, boolean canEdit, SquareType block)
    {
        if (!gameBoard[column][row].getEditable())
        {
            return false;
        }
        gameBoard[column][row].setMark(block);
        gameBoard[column][row].setEditable(canEdit);
        return true;
    }

    /**
     * randomPuzzle will create a random MarupekeGrid with the information
     * given to this method. Before it starts the random creation, it first checks that
     * no more than half the square in the grid are full, if they are, then the method will try
     * and fix that. A puzzle will keep generating until it creates one that is considered
     * legal (using the method isLegalGrid()).
     *
     * @param size Size of the Marupeke Grid
     * @param numFill Number of solid squares in the Marupeke Grid
     * @param numX Number of numX squares in the Marupeke Grid
     * @param numO Number of numO squares in the Marupeke Grid
     *
     * @return Returns the newly created legal MarupekeGrid
     */
    public static MarupekeGrid randomPuzzle(int size, int numFill, int numX, int numO) {
        int count = 0;
        int failureCount = 0;
        while((numFill + numX + numO > (size * size) / 2))
        {
            if (failureCount > 10) System.exit(0);
            try
            {
                    throw new TooManyMarkedSquares("Too many squares filled!");
            }
            catch (TooManyMarkedSquares e)
            {
                if (numFill > 1) numFill = numFill - 1;
                if (numX > 1) numX = numX - 1;
                if (numO > 1) numO = numO - 1;
                failureCount++;
            }
        }
        if (size*size - numFill - numX -numO > size*size)
        {
            return null;
        }
            MarupekeGrid randPuzzle = new MarupekeGrid(size);
            while (count != numFill) {
                if (randPuzzle.setSolid(new Random().nextInt(size), new Random().nextInt(size))) count += 1;
            }
            count = 0;
            while (count != numX) {
                if (randPuzzle.setX(new Random().nextInt(size), new Random().nextInt(size), false)) count += 1;
            }
            count = 0;
            while (count != numO) {
                if (randPuzzle.setO(new Random().nextInt(size), new Random().nextInt(size), false)) count += 1;
            }
            return randPuzzle;
        }

    /**
     * toString will print out the board, and give the player a
     * visualization of the board
     *
     * @return returns the string printBoard
     */
    public String toString()
    {
        StringBuilder printBoard = new StringBuilder();
        for (MarupekeTile[] marupekeTiles : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                printBoard.append(marupekeTiles[j].getMarkChar());
            }
            printBoard.append("\r\n");
        }
        return printBoard.toString();
    }

    /**
     * Unmarks the selected square if it is editable returning true. If it is not editable, a illegalitiesInGrid is created
     * and printed out, then returns false.
     *
     * @param column Column
     * @param row Row
     *
     * @return Returns true if the square was unmarked, false if it was not unmarked
     */
    public boolean unmark(int column, int row) {
        if (gameBoard[column][row].getEditable())
        {
            gameBoard[column][row].setMark(SquareType.EMPTY);
            return true;
        }
        System.out.println(illegalitiesInGrid("Replaced initial Block(RED)",column,row, '_'));
        return false;
    }

    /**
     * markX makes the selected square into an X only if
     * it is editable. If it is editable, then it checks if substituting
     * the square with X leaves the grid legal, if not, then an error message
     * is returned and the grid stays the same.
     *
     * @param column Column of the MarupekeGrid
     * @param row Row of the MarupekeGrid
     *
     * @return returns if the markX was successful or not
     */
    public boolean markX(int column, int row)
    {
        SquareType temp;
        if (gameBoard[column][row].getEditable())
        {
            temp = gameBoard[column][row].getMarkEnum();
            setX(column,row,true);
            if (!isLegalGrid(true))
            {
                gameBoard[column][row].setMark(temp);
                return false;
            }
            return true;
        }
        else
            {
                System.out.println(illegalitiesInGrid("Replaced initial block (RED)",column, row, 'X'));
        }
        return false;
    }

    /**
     * markO makes the selected square into an X only if
     * it is editable. If it is editable, then it checks if substituting
     * the square with O leaves the grid legal, if not, then an error message
     * is returned and the grid stays the same
     *
     * @param column Column
     * @param row Row
     *
     * @return returns if the markO was successful or not
     */
    public boolean markO(int column, int row) {
        SquareType temp;
        if (gameBoard[column][row].getEditable()) {
            temp = gameBoard[column][row].getMarkEnum();
            setO(column, row, true);
            if (!isLegalGrid(true)) {
                gameBoard[column][row].setMark(temp);
                return false;
            }
            return true;
        } else
            {
                System.out.println(illegalitiesInGrid("Replaced initial block (RED)",column, row, 'O'));
        }
        return false;
    }

    /**
     * Checks if the current Marupeke Puzzle is complete. It is done by checking
     * if the grid is legal, and if the grid is completely filled
     *
     * @return Returns true if the Marupeke Puzzles is complete or false if not
     */
    public boolean isPuzzleComplete()
    {
        return isLegalGrid(false) && isFull();
    }

    /**
     * Checks if the current Marupeke Puzzle is full (O, X or #). It returns false if the
     * current square selected (gameBoard.grid[i][j]) is SquareType.EMPTY ('_'). If no
     * SquareType.EMPTY are present in the grid, then returns true
     *
     * @return Returns true if the Marupeke Puzzles is full or false if not
     */
    public boolean isFull()
    {
        for (MarupekeTile[] marupekeTiles : gameBoard)
        {
            for (int j = 0; j < gameBoard.length; j++) {
                if (marupekeTiles[j].getMarkEnum() == SquareType.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the current Marupeke grid is legal. A Marupeke Grid is legal if there are
     * no more than two consecutive pieces of the same symbol anywhere in the grid. If there
     * are three or more consecutive (horizontally, vertically and diagonally) then returns false.
     * The error is then saved in a arraylist illegalitiesInGrid which then allows the error to
     * be printed with the exact location and type of error.
     *
     * @return If the grid is legal, returns true, If the grid is not legal, returns false
     */
    public boolean isLegalGrid(boolean playerPhase) {
        int max = gameBoard.length-2;
        int min = 1;
        SquareType curr;
        for (int i = 0; i < gameBoard.length; i++)
        {
            for (int j = 0; j < gameBoard.length; j++)
            {
                if (gameBoard[i][j].getMarkEnum() != SquareType.EMPTY)
                {
                    curr = gameBoard[i][j].getMarkEnum();
                    if (i < max && curr == gameBoard[i+1][j].getMarkEnum() && curr == gameBoard[i+2][j].getMarkEnum())
                    {
                        if (playerPhase) illegalitiesInGrid("Three vertical consecutive marks",i,j, curr.printChar());
                        return false;
                    }
                    if (j < max && curr == gameBoard[i][j+1].getMarkEnum() && curr == gameBoard[i][j+2].getMarkEnum())
                    {
                        if (playerPhase) illegalitiesInGrid("Three horizontal consecutive marks",i,j, curr.printChar());
                        return false;
                    }
                    if (i < max && j < max && curr == gameBoard[i+1][j+1].getMarkEnum() && curr == gameBoard[i+2][j+2].getMarkEnum())
                    {
                        if (playerPhase) illegalitiesInGrid("Three diagonal consecutive marks", i, j, curr.printChar());
                        return false;
                    }
                    if (i > min && j < max && curr == gameBoard[i-1][j+1].getMarkEnum() && curr == gameBoard[i-2][j+2].getMarkEnum())
                    {
                        if (playerPhase) illegalitiesInGrid("Three diagonal consecutive marks\"",i,j, curr.printChar());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns the char of enum grid[i][j], used for tests
     *
     * @param column Column of the MarupekeGrid
     * @param row Row of the MarupekeGrid
     *
     * @return Char of the selected gameBoard.grid[i][j]
     */
    public char returnChar(int column, int row) {
        return gameBoard[column][row].getMarkChar();
    }

    /**
     * illegalitiesInGrid will create a list, stating the reason why the
     * current MarupekeGrid is invalid. The reaosn will contain the type of error
     * (Horizontal, Vertical, Diagonal, Solid block), the index of where the error
     * stems from, and lastly the type of square that the program tried to place
     * (X, O, _ or #).
     *
     * @param type type of error
     * @param column column of the grid
     * @param row row of the grid
     * @param square character (X, O, _ or #) in question
     *
     * @return Returns the newely created list illegalitiesInGrid
     */
    public List<Reason> illegalitiesInGrid(String type, int column, int row, char square)
    {
        illegalitiesInGrid = new ArrayList<>();
        Reason dir = new Direction(type);
        illegalitiesInGrid.add(dir);
        Reason character = new Square(square);
        illegalitiesInGrid.add(character);
        Reason location = new Index(column,row);
        illegalitiesInGrid.add(location);
        return illegalitiesInGrid;
    }

    /**
     * Getter for illegalitiesInGrid. Used to print out the reason of the error
     *
     * @return returns a List<Reason>
     */
    public List<Reason> getReason()
    {
        return illegalitiesInGrid;
    }
}

