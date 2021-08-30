package Parser;

/**
 * @author ianw
 * A representation of what the user wants the game to do next.
 */
public class Command {
    private final CommandWord command;
    private int row = 0;
    private int column = 0;
    private String msg = "";

    /**
     * Initialise with a Command and a message (which may be empty)
     *
     * @param command ss
     * @param msg sss
     */
    public Command(CommandWord command, String msg) {
        this.command = command;
        this.msg = msg;
    }

    /**
     * Messages are currently only associated with "unknown" commands
     *
     * @return the message associated with the command
     */
    public String getMsg() {
        return msg;
    }


    /**
     * If we have a command that operated on coordinates, instantiate
     * with the correct row and column
     *
     * @param command ss
     * @param row ss
     * @param column ss
     */
    public Command(CommandWord command, int column, int row) {
        super();
        this.command = command;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Command " + command + ", row=" + row + ", column="
                + column;
    }

    /**
     * Principally to be used in a switch statement to decide on the
     * action to be taken, given this command
     *
     * @return the CommandWord
     */
    public CommandWord getCommand() {
        return command;
    }

    /**
     * Valid for commands which need a row and column value
     *
     * @return The row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Valid for commands which need a row and column value
     *
     * @return The column value
     */
    public int getColumn() {
        return column;
    }



}
