package belaquaa.demosnake.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameConfig {
    private final int boardWidth;
    private final int boardHeight;
    private final float speed;
}
