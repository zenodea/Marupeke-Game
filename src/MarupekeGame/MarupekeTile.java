package MarupekeGame;




public class MarupekeTile
{
    private boolean editable;
    private SquareType grid;

    public MarupekeTile()

    {
        editable = true;
        grid = SquareType.EMPTY;
    }


    public char getMarkChar()
    {
        return getMarkEnum().printChar();
    }


    public SquareType getMarkEnum()
    {
        return grid;
    }


    public void setMark (SquareType block)
    {
        grid = block;
    }

    public boolean getEditable()
    {
        return editable;
    }


    public void setEditable(boolean canEdit)
    {
        editable = canEdit;
    }


}

