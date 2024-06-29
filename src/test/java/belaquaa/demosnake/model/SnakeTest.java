package belaquaa.demosnake.model;

import belaquaa.demosnake.configuration.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    private Snake snake;

    @BeforeEach
    public void setUp() {
        snake = new Snake(new Point(5, 5), 3);
    }

    @Test
    public void testInitialPosition() {
        LinkedList<Point> expectedBody = new LinkedList<>();
        expectedBody.add(new Point(5, 5));
        expectedBody.add(new Point(4, 5));
        expectedBody.add(new Point(3, 5));

        assertEquals(expectedBody, snake.getBody());
    }

    @Test
    public void testMove() {
        snake.move();
        LinkedList<Point> expectedBody = new LinkedList<>();
        expectedBody.add(new Point(6, 5));
        expectedBody.add(new Point(5, 5));
        expectedBody.add(new Point(4, 5));

        assertEquals(expectedBody, snake.getBody());
    }

    @Test
    public void testGrow() {
        snake.grow();
        LinkedList<Point> expectedBody = new LinkedList<>();
        expectedBody.add(new Point(6, 5));
        expectedBody.add(new Point(5, 5));
        expectedBody.add(new Point(4, 5));
        expectedBody.add(new Point(3, 5));

        assertEquals(expectedBody, snake.getBody());
    }

    @Test
    public void testGetHead() {
        assertEquals(new Point(5, 5), snake.getHead());
    }

    @Test
    public void testChangeDirection() {
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());

        snake.move();
        LinkedList<Point> expectedBody = new LinkedList<>();
        expectedBody.add(new Point(5, 4));
        expectedBody.add(new Point(5, 5));
        expectedBody.add(new Point(4, 5));

        assertEquals(expectedBody, snake.getBody());
    }

    @Test
    public void testCheckCollision_NoCollision() {
        assertFalse(snake.checkCollision());
    }

    @Test
    public void testCheckCollision_WithCollision() {
        snake.grow();  // Extend snake to make it possible for collision
        snake.grow();
        snake.setDirection(Direction.LEFT);
        snake.move();  // Move left
        snake.setDirection(Direction.DOWN);
        snake.move();  // Move down
        snake.setDirection(Direction.RIGHT);
        snake.move();  // Move right
        snake.setDirection(Direction.UP);
        snake.move();  // Move up, collision should happen here

        assertTrue(snake.checkCollision());
    }
}
