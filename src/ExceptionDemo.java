class MyArraySizeException extends Exception {
    public MyArraySizeException(String message) {
        super(message);
    }

    public MyArraySizeException(String message, Throwable cause) {
        super(message, cause);
    }
}

class MyArrayDataException extends Exception {
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

public class ExceptionDemo {

    public static int processArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr == null) {
            throw new MyArraySizeException("Массив не инициализирован (null)");
        }
        if (arr.length != 4) {
            throw new MyArraySizeException("Ожидалось 4 строки, но получено: " + arr.length);
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                throw new MyArraySizeException("Строка " + i + " не инициализирована (null)");
            }
            if (arr[i].length != 4) {
                throw new MyArraySizeException(
                        String.format("Ожидалось 4 столбца в строке %d, но получено: %d", i, arr[i].length)
                );
            }
        }

        int sum = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String s = arr[i][j];
                try {
                    int x = Integer.parseInt(s);
                    sum += x;
                } catch (NumberFormatException e) {

                    throw new MyArrayDataException(i, j, s);
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        String[][] good = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String[][] badSize = {
                {"1", "2", "3"},
                {"4", "5", "6", "7"},
                {"8", "9", "10", "11"}
        };

        String[][] badData = {
                {"1", "2", "3", "4"},
                {"5", "6", "X", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int result = processArray(good);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.err.println("Ошибка размера массива: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.err.println("Ошибка данных: " + e.getMessage());
        } finally {
            System.out.println("Блок finally выполняется всегда (например, для очистки ресурсов).");
        }

        System.out.println();

        try {
            int result = processArray(badSize);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            System.out.println("Конец обработки badSize.");
        }

        System.out.println();

        try {
            int result = processArray(badData);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.err.println("Ошибка размера: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.err.println("Ошибка данных: " + e.getMessage());
        } finally {
            System.out.println("Конец обработки badData.");
        }

        System.out.println();

        try {
            int[] arr = {10, 20, 30};
            System.out.println("Элемент с индексом 5: " + arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Поймано исключение: " + e);
        } finally {
            System.out.println("Finally после попытки доступа к элементу массива.");
        }
    }
}
