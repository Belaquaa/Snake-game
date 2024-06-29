package belaquaa.demosnake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    public void testPointCreation() {
        Point point = new Point(5, 10);

        assertEquals(5, point.x());
        assertEquals(10, point.y());
    }

    @Test
    public void testPointEquality() {
        Point point1 = new Point(5, 10);
        Point point2 = new Point(5, 10);

        assertEquals(point1, point2);
    }
}
