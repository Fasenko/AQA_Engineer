import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FactorialTest {

    @Test
    public void testFactorialOf5() {
        assertEquals(Factorial.calculate(5), 120);
    }

    @Test
    public void testFactorialOf0() {
        assertEquals(Factorial.calculate(0), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeNumber() {
        Factorial.calculate(-5);
    }
}