package belaquaa.demosnake.configuration;

import belaquaa.demosnake.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Test
    public void testGameStateCreation() {
        Snake snake = new Snake(new Point(5, 5), 3);
        Apple apple = new Apple(new Point(10, 10));
        GameState gameState = new GameState(snake, apple);

        assertEquals(snake, gameState.snake());
        assertEquals(apple, gameState.apple());
    }
}
