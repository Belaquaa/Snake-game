package belaquaa.demosnake.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameConfigTest {

    @Test
    public void testGameConfigCreation() {
        GameConfig config = new GameConfig(20, 20, 1.0f);

        assertEquals(20, config.boardWidth());
        assertEquals(20, config.boardHeight());
        assertEquals(1.0f, config.speed());
    }
}
