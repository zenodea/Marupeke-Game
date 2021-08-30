package Parser;

import MarupekeGame.*;
import java.util.Scanner;

/**
 * @author ianw, bernhard
 * <p>
 * A very simple input line parser that parses each line written by the user to
 * the console. To use, create an object of this type, and then repeatedly call
 * getCommand.
 */
public class Parser {
    private final Scanner input;
    private final boolean[][] visited = new boolean[30][30];
    private Marupeke main;
    public Parser() {
        input = new Scanner(System.in);
    }
    /**
     * Parse the input line, converting the first word encountered into a
     * command, and then passing any further arguments that make sense.
     * CHANGES TO THE PARSER
     * Limits set for the grid (not smaller than 4, not bigger than 10)
     * MarupekeGrid will be generated until a legal grid is created using code
     * while (!Marupeke.getGrid().isLegalGrid(false))
     *
     * @return the parsed command
     */
    public Command getCommand() {
        String inputLine = input.nextLine();
        Scanner scanner = new Scanner(inputLine);
        if (scanner.hasNext()) {
            String str = scanner.next();
            CommandWord cw = CommandWord.getCommandWord(str);
            if (cw == CommandWord.UNKNOWN) {
                return new Command(cw, "Unknown command: " + str);
            } else if (cw == CommandWord.QUIT) {
                return new Command(cw, "Bye bye");
            } else if (cw == CommandWord.NEW) {
                if (main.getGameSession() == null)
                {
                if (scanner.hasNextInt()) {
                    int size = scanner.nextInt();
                    main = new Marupeke();
                    if (main.getGameSession() == null)
                    {
                        System.out.println("\nMarupeke Grid is not creatable with this input\n" +
                                           "Please, enter a different size\n");
                        getCommand();
                    }
                    if (main.getGameSession().getGameBoard().length < 4)
                    {
                        System.out.println("\nInput too low\n");
                        main = new Marupeke();
                        getCommand();
                    }
                    if (main.getGameSession().getGameBoard().length > 10)
                    {
                        System.out.println("\nInput too high\n");
                        main = new Marupeke();
                        getCommand();
                    }
                    while (!main.getGameSession().isLegalGrid(false))
                    {
                        main = new Marupeke(size,DiffLevels.easy);
                    }
                    return new Command(cw, size, size);
                }
                return new Command(cw, 0, 0);
                } else
                    {
                    return new Command(CommandWord.NEW, cw.getWord() + "Marupeke grid exists already!\nPlay the game!!!\n\n");
                }
            } else {
                if (scanner.hasNextInt())
                {
                    int row = scanner.nextInt();
                    if (scanner.hasNextInt())
                    {
                        int col = scanner.nextInt();
                        return new Command(cw, row, col);
                    }
                }
                return new Command(CommandWord.UNKNOWN, cw.getWord() + " needs two integer arguments");
            }
        } else {
            return new Command(CommandWord.UNKNOWN, "Please tell me what to do");
        }

    }
}

