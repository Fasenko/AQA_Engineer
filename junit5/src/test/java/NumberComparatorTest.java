import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberComparatorTest {

    @Test
    void testCompareEqual() {
        assertEquals(0, NumberComparator.compare(5, 5));
    }

    @Test
    void testCompareLess() {
        assertEquals(-1, NumberComparator.compare(3, 5));
    }

    @Test
    void testCompareGreater() {
        assertEquals(1, NumberComparator.compare(8, 2));
    }
}