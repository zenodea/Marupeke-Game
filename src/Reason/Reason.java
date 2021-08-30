package Reason;

public abstract class Reason
{
    protected int column, row;
    protected String dir;
    protected char square;

    public abstract String violation();

    public abstract String toString();
}

