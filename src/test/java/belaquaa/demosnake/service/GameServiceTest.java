package belaquaa.demosnake.service;

import belaquaa.demosnake.enums.Direction;
import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.model.Point;
import belaquaa.demosnake.model.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {

    private GameService gameService;

    @Test
    public void testResetGame() {
        gameService.resetGame();
        Snake snake = gameService.getSnake();
        Apple apple = gameService.getApple();
        assertNotNull(snake);
        assertNotNull(apple);
        assertEquals(0, gameService.getScore());
    }

    @Test
    public void testUpdateGame() {
        gameService.resetGame();
        boolean collision = gameService.updateGame();
        assertFalse(collision);
    }

    @Test
    public void testUpdateDirection() {
        gameService.resetGame();
        gameService.updateDirection(Direction.UP);
        assertEquals(Direction.UP, gameService.getSnake().getDirection());
        gameService.updateDirection(Direction.DOWN);
        assertEquals(Direction.UP, gameService.getSnake().getDirection()); // Should not change to opposite direction
    }

    @Test
    public void testMoveSnake() {
        gameService.resetGame();
        Point initialHeadPosition = gameService.getSnake().getHead();
        gameService.moveSnake();
        Point newHeadPosition = gameService.getSnake().getHead();
        assertNotEquals(initialHeadPosition, newHeadPosition);
    }

    @Test
    public void testGenerateApple() {
        gameService.resetGame();
        Apple initialApple = gameService.getApple();
        gameService.moveSnake(); // Should generate a new apple if snake eats it
        Apple newApple = gameService.getApple();
        if (gameService.getSnake().getHead().equals(initialApple.position())) {
            assertNotEquals(initialApple, newApple);
        }
    }

    @Test
    public void testIsOutOfBounds() {
        assertFalse(gameService.isOutOfBounds(new Point(0, 0)));
        assertTrue(gameService.isOutOfBounds(new Point(-1, 0)));
        assertTrue(gameService.isOutOfBounds(new Point(0, -1)));
        assertTrue(gameService.isOutOfBounds(new Point(gameService.getBoardWidth(), 0)));
        assertTrue(gameService.isOutOfBounds(new Point(0, gameService.getBoardHeight())));
    }
}
