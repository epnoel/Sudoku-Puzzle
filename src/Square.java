public class Square {

    private int value;
    private int row;
    private int col;
    private int box;
    private boolean isRevealed;

    Square() {
        value = 0;
        row = -1;
        col = -1;
        box = 0;
        isRevealed = false;
    }

    Square(int value, int row, int col, int box) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.box = box;
        isRevealed = false;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public int getBox() {
        return box;
    }

    public void setRevealed(boolean bool) {
        isRevealed = bool;
    }

    public boolean getRevealed() {
        return isRevealed;
    }

    public void resetSquare()
    {
        value = 0;
        row = -1;
        col = -1;
        box = 0;
        isRevealed = false;
    }

}
