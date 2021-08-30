package Reason;

public class Square extends Reason
{
    public Square(char square)
    {
        this.square = square;
    }

    public String violation()
    {
        return  "\nSymbol Inputted: " + square + "\n";
    }

    @Override
    public String toString()
    {
        return violation();
    }

}
