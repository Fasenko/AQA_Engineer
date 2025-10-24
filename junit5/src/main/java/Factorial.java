public class Factorial {

    public static long calculate(int n) {
        // ИСПРАВЛЕНО: Убраны фигурные скобки, применено форматирование
        if (n < 0)
            throw new IllegalArgumentException("Negative number not allowed");

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
