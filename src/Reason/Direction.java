package Reason;

public class Direction extends Reason{
    public Direction(String dir)
    {
        this.dir = dir;
    }

    public String violation()
    {
        return  "Type of Error: " + dir + "\n";
    }

    @Override
    public String toString()
    {
        return violation();
    }
}
