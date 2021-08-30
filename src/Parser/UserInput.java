package Parser;
import MarupekeGame.*;

public class UserInput {
    Parser parser = new Parser();
    private Marupeke main;
    Command c = parser.getCommand();

    /**
     * inValid makes sure the user can not input a number
     * for the column and row bigger than the grid itself
     *
     * @return Returns if the user input is valid or not, true if it is not valid, false if it is valid
     */
    public boolean inValid()
    {
        if (main.getGameSession() != null)
        {
            if (c.getColumn() > main.getGameSession().getGameBoard().length)
            {
                System.out.println("\nColumn too high");
                return true;
            }
            if (c.getColumn() < 1)
            {
                System.out.println("\nColumn too small");
                return true;
            }
            if (c.getRow() > main.getGameSession().getGameBoard().length)
            {
                System.out.println("\nrow too high");
                return true;
            }
            if (c.getRow() < 1)
            {
                System.out.println("\nrow too small");
                return true;
            }
            return false;
        }
        System.out.println("\n:::Create gameboard first:::\n");
        return true;
    }

    /**
     * execute will translate the user input into commands
     * MARKO will mark a square O (if valid)
     * MARKX will mark a square X (if valid)
     * CLEAR will clear a square (if valid)
     * QUIT will quit the game (if valid)
     *
     */
    private void execute(Command c) {
        switch (c.getCommand()) {
            case MARKO:
                if (inValid())
                {
                    break;
                }
                main.getGameSession().markO(c.getColumn() - 1,c.getRow() - 1);
                break;

            case MARKX:
                if (inValid())
                {
                    break;
                }
                main.getGameSession().markX(c.getColumn() - 1,c.getRow() - 1);
                break;

            case CLEAR:
                if (inValid())
                {
                    break;
                }
                main.getGameSession().unmark(c.getColumn() - 1,c.getRow() - 1);
                break;

            case QUIT:
                System.exit(0);
                break;

            default:
                System.out.println(c);
                break;
        }
        printPrompt(c.getMsg());
    }

    /**
     * commandLine executes the Command C
     *
     */
    private void commandLine()
    {
            execute(c);
    }

    /**
     * printPrompt will print out the String msg found in
     * the class Command
     *
     */
    private void printPrompt(String msg)
    {
        System.out.println(msg);
    }

    public static void main()
    {
        UserInput input = new UserInput();
        input.commandLine();
    }
}
