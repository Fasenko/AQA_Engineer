import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class CalculatorTest {

    @Test
    public void testAdd() {
        assertEquals(Calculator.add(5, 3), 8);
    }

    @Test
    public void testSubtract() {
        assertEquals(Calculator.subtract(5, 3), 2);
    }

    @Test
    public void testMultiply() {
        assertEquals(Calculator.multiply(5, 3), 15);
    }

    @Test
    public void testDivide() {
        assertEquals(Calculator.divide(5, 2), 2.5);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        Calculator.divide(5, 0);
    }
}
