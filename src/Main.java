public class Main {
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
            int result = ExceptionDemo.processArray(good);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.err.println("Ошибка размера массива: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.err.println("Ошибка данных: " + e.getMessage());
        }

        System.out.println();

        try {
            int result = ExceptionDemo.processArray(badSize);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        System.out.println();

        try {
            int result = ExceptionDemo.processArray(badData);
            System.out.println("Сумма элементов: " + result);
        } catch (MyArraySizeException e) {
            System.err.println("Ошибка размера: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.err.println("Ошибка данных: " + e.getMessage());
        }

        System.out.println();

        try {
            int[] arr = {10, 20, 30};
            System.out.println("Элемент с индексом 5: " + arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Поймано исключение: " + e);
        }
    }
}
