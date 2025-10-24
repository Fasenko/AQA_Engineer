import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleAreaTest {

    @Test
    void testTriangleArea() {
        assertEquals(10.0, TriangleArea.calculate(4, 5));
    }

    @Test
    void testInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> TriangleArea.calculate(0, 5));
        assertThrows(IllegalArgumentException.class, () -> TriangleArea.calculate(5, -1));
    }
}
