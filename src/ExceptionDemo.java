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
}

