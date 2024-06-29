package belaquaa.demosnake.configuration;

import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.model.Snake;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameState {
    private final Snake snake;
    private final Apple apple;
}