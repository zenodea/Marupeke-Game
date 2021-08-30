package Reason;

public class Index extends Reason {
    public Index(int column, int row)
    {
        this.column = column+1;
        this.row = row+1;
    }

    public String violation()
    {
        return "\nStart of the Error - Column: " + column + " Row: " + row;
    }

    @Override
    public String toString()
    {
        return violation();
    }
}
