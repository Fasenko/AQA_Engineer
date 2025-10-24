import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAdd() {
        assertEquals(8, Calculator.add(5, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(2, Calculator.subtract(5, 3));
    }

    @Test
    void testMultiply() {
        assertEquals(15, Calculator.multiply(5, 3));
    }

    @Test
    void testDivide() {
        assertEquals(2.5, Calculator.divide(5, 2));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> Calculator.divide(5, 0));
    }
}
