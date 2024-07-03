package belaquaa.demosnake.controller;

import belaquaa.demosnake.configuration.GameConfig;
import belaquaa.demosnake.configuration.GameState;
import belaquaa.demosnake.configuration.Score;
import belaquaa.demosnake.configuration.Direction;
import belaquaa.demosnake.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping("/state")
    public GameState getGameState() {
        return new GameState(gameService.getSnake(), gameService.getApple());
    }

    @GetMapping("/config")
    public GameConfig getGameConfig() {
        return new GameConfig(gameService.getBoardWidth(), gameService.getBoardHeight(), gameService.getSpeed());
    }

    @GetMapping("/speed")
    public float getCurrentSpeed() {
        return gameService.getSpeed();
    }

    @GetMapping("/score")
    public Score getScore() {
        return new Score(gameService.getScore(), gameService.getBestScore());
    }

    @PostMapping("/direction")
    public void setDirection(@RequestBody Direction direction) {
        gameService.updateDirection(direction);
    }

    @PostMapping("/update")
    public boolean updateGame() {
        return gameService.updateGame();
    }

    @PostMapping("/reset")
    public void resetGame() {
        gameService.resetGame();
    }
}