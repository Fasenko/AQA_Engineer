    public class Main {

        public static void main(String[] args) {
            printThreeWords();       // 1

            checkSumSign();          // 2

            printColor();            // 3

            compareNumbers();        // 4

            System.out.println(isSumBetween10And20(7, 5)); // 5

            printPositiveOrNegative(-10); // 6

            System.out.println(isNegative(-5)); // 7

            printString("Строка", 4); // 8

            System.out.println(isLeapYear(2024)); // 9

            invertArray();           // 10

            fillArray();             // 11

            changeArray();           // 12

            fillDiagonal();          // 13

            int[] arr = createArray(5, 7); // 14
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // 1
        public static void printThreeWords() {
            System.out.println("Orange");
            System.out.println("Banana");
            System.out.println("Apple");
        }

        // 2
        public static void checkSumSign() {
            int a = 5;
            int b = 10;
            int sum = a + b;
            if (sum >= 0) {
                System.out.println("Сумма положительная");
            } else {
                System.out.println("Сумма отрицательная");
            }
        }

        // 3
        public static void printColor() {
            int value = 50;
            if (value <= 0) {
                System.out.println("Красный");
            } else if (value <= 100) {
                System.out.println("Желтый");
            } else {
                System.out.println("Зеленый");
            }
        }

        // 4
        public static void compareNumbers() {
            int a = 7;
            int b = 3;
            if (a >= b) {
                System.out.println("a >= b");
            } else {
                System.out.println("a < b");
            }
        }

        // 5
        public static boolean isSumBetween10And20(int a, int b) {
            int sum = a + b;
            return sum >= 10 && sum <= 20;
        }

        // 6
        public static void printPositiveOrNegative(int x) {
            if (x >= 0) {
                System.out.println("Число положительное");
            } else {
                System.out.println("Число отрицательное");
            }
        }

        // 7
        public static boolean isNegative(int x) {
            return x < 0;
        }

        // 8
        public static void printString(String str, int times) {
            for (int i = 0; i < times; i++) {
                System.out.println(str);
            }
        }

        // 9
        public static boolean isLeapYear(int year) {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        }

        // 10
        public static void invertArray() {
            int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (arr[i] == 0) ? 1 : 0;
            }
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // 11
        public static void fillArray() {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i + 1;
            }
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // 12
        public static void changeArray() {
            int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] < 6) {
                    arr[i] *= 2;
                }
            }
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // 13
        public static void fillDiagonal() {
            int size = 5;
            int[][] arr = new int[size][size];
            for (int i = 0; i < size; i++) {
                arr[i][i] = 1; // главная диагональ
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
        }

        // 14
        public static int[] createArray(int len, int initialValue) {
            int[] arr = new int[len];
            for (int i = 0; i < len; i++) {
                arr[i] = initialValue;
            }
            return arr;
        }
    }

