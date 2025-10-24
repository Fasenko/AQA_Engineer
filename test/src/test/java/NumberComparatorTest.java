import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class NumberComparatorTest {

    @Test
    public void testCompareEqual() {
        assertEquals(NumberComparator.compare(5, 5), 0);
    }

    @Test
    public void testCompareLess() {
        assertEquals(NumberComparator.compare(3, 5), -1);
    }

    @Test
    public void testCompareGreater() {
        assertEquals(NumberComparator.compare(8, 2), 1);
    }
}
