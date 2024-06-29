package belaquaa.demosnake.configuration;

import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.model.Snake;

public record GameState(Snake snake, Apple apple) {
}