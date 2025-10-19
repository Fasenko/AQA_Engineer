public class MyArrayDataException extends Exception {
    private final int row;
    private final int col;
    private final String value;

    public MyArrayDataException(int row, int col, String value) {
        super(String.format("Неправильные данные в ячейке [%d][%d]: \"%s\"", row, col, value));
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getValue() {
        return value;
    }
}


