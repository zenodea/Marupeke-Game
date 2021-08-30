package MarupekeGame;

import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *  The Marupeke Class stores the data for the MarupekeGUI(which are the controls and visuals). This class
 *  holds the MarupekeGrid data, the number of moves and hints, a Stack that stores previous move, the fileWriter and
 *  lastly the username. This class also holds all the methods to modify said data.
 *
 */
public class Marupeke {
    private  int count = 0, hintNum = 4;
    private  MarupekeGrid gameSession, initialSession;
    private  final Stack<String> prev = new Stack<>();
    private  FileWriter fileWriter;
    private TextField username;

    //OLD PARSER
    /*
    public static void main(String[] args)
    {
        MarupekeGrid gameSession = null;
        System.out.println("\n:::New Game:::");
        System.out.println("\n:::Controls:::");
        System.out.println("\n:::New (int) = Creates a game board of size (int):::");
        System.out.println("\n:::xmark/omark/clear (column) (row) = Marks or Unmarks the square at location (column) (row):::");
        System.out.println("\n:::Quit = leave game:::\n\n");
        System.out.println("\n:::Create Board:::\n");
        while (gameSession == null)
        {
            UserInput.main();
            System.out.println(gameSession);
        }
        while (!gameSession.isPuzzleComplete())
        {
            System.out.print(">");
            UserInput.main();
            System.out.println(gameSession);
        }
        System.out.println(":::WIN:::");
    }
*/

    /**
     * This constructor creates a new Marupeke object, that contains a newly generated MarupekeGrid object.
     * This constructor requires two parameters. First one is the size of the grid. The second one is the difficulty.
     * A higher difficulty means more initial blocks (not editable), and less hints.
     *
     * @param x this int parameter represents the size of the, to be created, game grid
     * @param diff this DiffLevels parameter gives the difficulty of the, to be created, game grid
     */
    public Marupeke (int x, DiffLevels diff)
    {
        //DIFF EASY IS y = 1
        int y = 1;
        if (diff == DiffLevels.medium)
        {
            y = x/3;
            hintNum = 3;
        }
        else if (diff == DiffLevels.hard)
        {
            y = x/2;
            hintNum = 2;
        }
        MarupekeGrid real = null;
        MarupekeGrid temp = MarupekeGrid.randomPuzzle(x,y,y,y);
        //loops until the current dfs finds an answer
        while (!temp.isPuzzleComplete())
        {
                   temp = MarupekeGrid.randomPuzzle(x, y, y, y);
                   real = copyGrid(temp);
                   temp = dfs(temp);
        }
        // Prints out complete grid
        // System.out.println(temp);
        initialSession = copyGrid(real);
        gameSession = real;
    }

    /**
     * The empty constructor for the Marupeke class is utilised when a marupeke board is already present. For example,
     * when a player decides to resume a previous game, this constructor is used.
     *
     */
    public Marupeke () {
    }

    /**
     * The hint() method gives the player a hint of where to place their blocks in the current MarupekeGrid (temp in this case).
     * It does so by running the findEmptySpots() method. After the method has two MarupekeGrids, it compares both, and if one
     * has a different tile, then said tile is returned as a String information as a hint.
     *
     * @param temp the player current MarupekeGrid
     *
     * @return returns a string containing the location where the player can place the block alongside the number of times to
     * go back to be able to place said block.
     */
    public String hint(MarupekeGrid temp) {
        if (hintNum == 0) return "You have used all your hints!";
        MarupekeGrid solution = copyGrid(temp);
        solution = findEmptySpot(solution);
        if (solution == null) return "No moves possible, go back!";
        for (int i = 0; i < temp.getGameBoard().length; i++)
        {
            for (int j = 0; j < temp.getGameBoard().length; j++)
            {
                if (solution.getGameBoard()[i][j].getMarkChar() != temp.getGameBoard()[i][j].getMarkChar())
                {
                    hintNum--;
                    return "Add symbol in location col = " + (i+1) + " row: " + (j+1) + " Character: " + solution.getGameBoard()[i][j].getMarkChar();
                }
            }
        }
        return null;
    }

    /**
     * findEmptySpots() method is a method that returns a MarupekeGrid with an additional tile placed in.
     * Said tile is the tile that the game will give to the player as a hint. It loops through the MarupekeGrid temp
     * and when one empty spot is found, it tries to first mark it with x. If that fails, then it marks it with O. If there
     * are no possible moves, then null is returned.
     *
     * @param temp Current Game session
     * @return Returns modified MarupekeGrid with hint inside.
     */
    public MarupekeGrid findEmptySpot(MarupekeGrid temp)
    {
        MarupekeGrid hintBoard = copyGrid(temp);
        for ( int i = 0; i < temp.getGameBoard().length; i++)
        {
            for ( int j = 0; j < temp.getGameBoard().length; j++)
            {
                if (temp.getGameBoard()[i][j].getMarkChar() == '_')
                {
                    if (hintBoard.markX(i,j)) return hintBoard;
                    if (hintBoard.markO(i,j)) return hintBoard;
                }
            }
        }
        return null;
    }

    /**
     * the dfs() method does a depth first search of the given MarupekeGrid (temp in this case).
     * Temp is the initial state, while the returned MarupekeGrid is the final state if temp is completable.
     * It uses ArrayDeque as stacks as its more efficient. This method is called inside a while loop, so it will run
     * until MarupekeGrid temp is completable.
     *
     * @param temp the initial state of the MarupekeGrid
     *
     * @return returns a MarupekeGrid that is completable, if not, returns the initial state MarupekeGrid
     */
    public MarupekeGrid dfs(MarupekeGrid temp)
    {
        ArrayDeque<MarupekeGrid> stack = new ArrayDeque<>();
        LinkedHashSet<String> visited = new LinkedHashSet<>();
        stack.add(temp);
        while (!stack.isEmpty())
        {
            MarupekeGrid x = stack.pop();
            visited.add(String.valueOf(x));
            if (x.isPuzzleComplete()) return x; //end state
            for (MarupekeGrid child : getChild(x))
            {
                if (!visited.contains(String.valueOf(child)) && !stack.contains(child))
                {
                    stack.push(child);
                }
            }
        }
        return temp;
    }

    /**
     * getChild() method collects all the possible moves and returns them as array list of MarupekeGrid. This is
     * done by looping throughout all possible actions on the current empty spot. At each loop (if the marking is succesfull)
     * then the new MarupekeGrid object is added to the copies ArrayList<MarupekeGrid>.
     *
     * @param temp this pair field contains the last location of the last move made, and the current MarupekeGrid
     *
     * @return returns all the possible moves on the current game board (8 directions) as an arrayList of object pairs
     */
    public ArrayList<MarupekeGrid> getChild(MarupekeGrid temp)
    {
        ArrayList<MarupekeGrid> copies = new ArrayList<>();
        for ( int i = 0; i < temp.getGameBoard().length; i++)
        {
            for ( int j = 0; j < temp.getGameBoard().length; j++)
            {
                if (temp.getGameBoard()[i][j].getMarkChar() == '_')
                {
                    MarupekeGrid tempXMark = copyGrid(temp);
                    tempXMark.markX(i,j);
                    copies.add(tempXMark);
                    MarupekeGrid tempOMark = copyGrid(temp);
                    tempOMark.markO(i,j);
                    copies.add(tempOMark);
                    return copies;
                }
            }
        }
        return copies;
    }

    /**
     * copyGrid() is a method that copies a MarupekeGrid (in this case temp), and returns said copy. This is done to
     * create a new MarupekeGrid Object that won't modify the temp MarupekeGrid Object. A new MarupekeGrid is created,
     * and in a nested for loop, all the elements of MarupekeGrid temp are inserted in the copy MarupekeGrid
     *
     * @param temp is the desired MarupekeGrid to copy
     * @return returns copy of MarupekeGrid temp
     */
    public static MarupekeGrid copyGrid(MarupekeGrid temp)
    {
        MarupekeGrid copy = new MarupekeGrid(temp.getGameBoard().length);
        for (int i = 0; i < temp.getGameBoard().length; i++) {
            for (int j = 0; j < temp.getGameBoard().length; j++)
            {
                copy.getGameBoard()[i][j].setMark(temp.getGameBoard()[i][j].getMarkEnum());
                copy.getGameBoard()[i][j].setEditable(temp.getGameBoard()[i][j].getEditable());
            }
        }
        return copy;
    }

    /**
     * gridTxt() is a method that makes a 2d array of char (in this case called temp) and translates it onto a
     * Marupeke Grid. An empty MarupekeGrid is created with the size of half the size of one of the arrays in the char temp
     * (Since, the char temp has both the initialSession(to maintain uneditable blocks) and the session left previously)
     * This method first loops throughout the first grid, and inserts uneditable symbols accordingly (Also inserts them in another
     * MarupekeGrid which represents the initialSession. The second loop loops through the second grid and inserts
     * all the symbols in the MarupekeGrid (uneditable symbols will remain uneditable).
     *
     * @param temp 2d char array that contains the characters of the text file the player has selected. In the case of this program,
     *             the 2d char array represents two Marupeke Grids.
     */
    public void gridTxt(char[][] temp)
    {
        MarupekeGrid gameSessionCopy = new MarupekeGrid(temp.length/2);
        MarupekeGrid initialSessionCopy = new MarupekeGrid(temp.length/2);
        int first = temp.length/2;
        int second = temp.length;
        for (int i = 0; i < first; i++) {
            for (int j = 0; j < first; j++)
            {
                if (temp[i][j] == 'X') gameSessionCopy.setX(i,j,false);
                else if (temp[i][j] == 'O') gameSessionCopy.setO(i,j,false);
                else if (temp[i][j] == '#') gameSessionCopy.setSolid(i,j);

                if (temp[i][j] == 'X') initialSessionCopy.setX(i,j,false);
                else if (temp[i][j] == 'O') initialSessionCopy.setO(i,j,false);
                else if (temp[i][j] == '#') initialSessionCopy.setSolid(i,j);
            }
        }
        for (int i = first; i < second; i++) {
            for (int j = 0; j < first; j++)
            {
                if (temp[i][j] == 'X')
                {
                    gameSessionCopy.setX(i-(temp.length/2),j,true);
                }
                else if (temp[i][j] == 'O')
                {
                    gameSessionCopy.setO(i-(temp.length/2),j,true);
                }
            }
        }
        gameSession = gameSessionCopy;
        initialSession = initialSessionCopy;
    }

    /**
     * the changeChar() method is the method that reads the move of the player and makes the changes on the current
     * MarupekeGrid gamesession. When the method is called, two ints are given representing the location of the
     * changes wanted. The method then reads what char the player has selected (Using the selectedMove() Squaretype
     * field). The method then places the block accordingly. If the placing of the block does not go accordingly, then
     * nothing is placed, and the showError() alert is called, giving the user the information necessary to understand why
     * the move did not work.
     *
     * @param i represents the column of the change desired
     * @param j represents the row of the change desired
     */
    public String changeChar(int i, int j, SquareType item)
    {
        char sub = gameSession.returnChar(i,j);
        if (item == SquareType.X)
        {
            if (!gameSession.markX(i,j)) MarupekeGUI.showError(String.valueOf(gameSession.getReason()));
            else
            {
                prev.add(i+","+j+","+sub);
                count++;
                return "Column: " + (i+1) + " Row: " + (j+1) + " Mark: " + "X";
            }
        }
        if (item == SquareType.O)
        {
            if (!gameSession.markO(i,j)) MarupekeGUI.showError(String.valueOf(gameSession.getReason()));
            else
            {
                prev.add(i+","+j+","+sub);
                count++;
                return "Column: " + (i+1) + " Row: " + (j+1) + " Mark: " + "O";
            }
        }
        if (item == SquareType.EMPTY)
        {
            if (!gameSession.unmark(i,j)) MarupekeGUI.showError(String.valueOf(gameSession.getReason()));
            else
            {
                count++;
                prev.add(i+","+j+",_"+sub);
                return "Column: " + (i+1) + " Row: " + (j+1) + " Mark: " + "_";
            }
        }
        return "Column: " + " Row: " + " Mark: " + " ";
    }

    /**
     * createFile() creates the name of a file using the field endFile to determine the name. If the name of the file
     * exists already, the num is then increased by one. (for example DoneGame_Mario1, DoneGame_Mario2...).
     * After creating the file, a new FileWriter writes inside the file, the final gameSession MarupekeGrid.
     *
     */
    public void createFile()
    {
        String name = "Games/DoneGame_" + username.getText();
        File moves = new File(name);
        for (int num = 1; moves.exists(); num++)
        {
            name = "Games/DoneGame_" + username.getText() + num + ".txt";
            moves = new File(name);
        }
        {
            try {
                fileWriter = new FileWriter(name);
                fileWriter.write(gameSession.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * saveGrid() method is used to create a file of a Marupeke game that has yet to end. it first creates a new file
     * of the name "OngoingGame_"username". If said name is taken, then the num next to the username increases by one.
     * After the file has been created, the String information is inserted inside said file.
     *
     * @param information this String field contains all the informations necessary for the program to later on
     *                    understand where the player has left off. The informations include the initial MarupekeGrid,
     *                    the current MarupekeGrid, the number of moves made by the player, the number of hints left,
     *                    and lastly the username of the player.
     */
    public void saveGrid(String information)
    {
        String name = "Ongoing_Games/OngoingGame_"+ username.getText();
        File moves = new File(name);
        for (int num = 1; moves.exists(); num++)
        {
            name = "Ongoing_Games/OngoingGame_" + username.getText() + num + ".txt";
            moves = new File(name);
        }
        {
            try {
                fileWriter = new FileWriter(name);
                fileWriter.write(information);
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method goBack() allows the player to go back one move. This is done by utilising the prev Stack of strings.
     * Each element of the stack contains an two ints and a char, all separated by a coma. This allows the method to get the row and column
     * of the previous move alongside the char. Depending on the char, the board replaces said block with the previous move.
     *
     */
    public void goBack()
    {
        String x = prev.pop();
        String info[] = x.split(",");
        int row = Integer.parseInt(info[0]);
        int col = Integer.parseInt(info[1]);
        String character = info[2];
        switch (character) {
            case "X":
                gameSession.markX(row, col);
                break;
            case "O":
                gameSession.markO(row, col);
                break;
            case "_":
                gameSession.unmark(row, col);
                break;
        }
    }

    /**
     * returnGrid() method goes throught a file using a scanner (File field named temp in this case). It does so by first
     * scanning the lines of the grid in the File. (The lenght is found by the getting the number of chars on the first line
     * since the grids are always size*size). This is then multiplied by two. This is because the file contains two grids, one
     * after the other. The first grid represents the intial grid (used to find which block is uneditable), and the second one
     * is the same grid with all the blocks the user inserted manualy (all editable). This two grids are saved in an array of chars.
     * Then the next lines of the file are scanned(after the two grids). The lines contain the number of moves the player had made,
     * the number of hints remaining and lastly the username of said player.
     *
     * @param temp represents the File selected by the player
     *
     */
    public void  returnGrid(File temp)
    {
        try {
            Scanner scannerLength = new Scanner(temp);
            int lengthArray = scannerLength.nextLine().toCharArray().length;
            char[][] blocks = new char[lengthArray*2][lengthArray*2];
            Scanner scanner = new Scanner(temp);
            for(int i=0; i < lengthArray*2; i++)
            {
                blocks[i] = scanner.nextLine().toCharArray();
            }
            gridTxt(blocks);
            count = Integer.parseInt(scanner.nextLine());
            hintNum = Integer.parseInt(scanner.nextLine());
            username = new TextField(scanner.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * getGameSession() is a getter for the field MarupekeGrid gameSession
     *
     * @return returns the field MarupekeGrid gameSession
     */
    public MarupekeGrid getGameSession()
    {
        return gameSession;
    }

    /**
     * geIntialSession() is a getter for the field MarupekeGrid intialSession
     *
     * @return returns the field MarupekeGrid initialSession
     */
    public MarupekeGrid getInitialSession()
    {
        return initialSession;
    }

    /**
     * getInformation method is utilised to print on the screen information specific to this Marupeke object's grid.
     * if int i is 1, then number of moves made is returned (count), if it's anything else, then the number of hints
     * remaining is given.
     *
     * @param i integer used to select the information needed in the MarupekeGUI class.
     * @return depending on int i, it returns either the number of moves made, or the number of hints remaining
     */
    public int getInformation(int i)
    {
        if (i == 1) return count;
        return hintNum;
    }

    /**
     * getPrev() returns the field prev, which rapresent the previous move made by the player. This method
     * is utilised for the MarupekeGUI class.
     *
     * @return return field prev
     */
    public Stack<String> getPrev()
    {
        return prev;
    }

    /**
     * sets the Textfield username for the Marupeke Object
     *
     * @param temp TextField received from the MarupekeGUI
     */
    public void setUsername(TextField temp)
    {
        username = temp;
    }

    /**
     * getter for the TextField username
     *
     */
    public TextField getUsername()
    {
        return username;
    }
}
