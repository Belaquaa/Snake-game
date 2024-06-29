package belaquaa.demosnake.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    @Test
    public void testScoreCreation() {
        Score score = new Score(5, 10);

        assertEquals(5, score.score());
        assertEquals(10, score.bestScore());
    }
}
