package belaquaa.demosnake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppleTest {

    @Test
    public void testApplePosition() {
        Point point = new Point(10, 10);
        Apple apple = new Apple(point);

        assertEquals(point, apple.position());
    }
}
