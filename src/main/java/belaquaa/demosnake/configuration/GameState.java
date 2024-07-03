package belaquaa.demosnake.configuration;

import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.model.Obstacle;
import belaquaa.demosnake.model.Snake;

import java.util.List;

public record GameState(Snake snake, Apple apple, List<Obstacle> obstacles) {
}
