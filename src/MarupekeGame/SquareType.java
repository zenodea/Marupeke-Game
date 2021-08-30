package MarupekeGame;

public enum SquareType
{
    X('X'), O('O'), SOLID('#'), EMPTY('_');

    private final char letter;

    SquareType(char letter) {
        this.letter = letter;
    }

    /**
     * Prints out the char of the specific enum
     * Example: FILLED = '#'
     *
     * @return Returns the char of the enum
     */
    public char printChar() {
        return letter;
    }
}
