package belaquaa.demosnake.service;

import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.model.Direction;
import belaquaa.demosnake.model.Snake;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import belaquaa.demosnake.model.Point;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Getter
public class GameService {
    private Snake snake;
    private Apple apple;
    private final int boardWidth;
    private final int boardHeight;
    private final int initialLength;
    private final int initialSpeed;
    private final Random random = new Random();
    private float speed;
    private int score;
    private int bestScore;

    public GameService(@Value("${game.board.width}") int boardWidth,
                       @Value("${game.board.height}") int boardHeight,
                       @Value("${game.snake.initialLength}") int initialLength,
                       @Value("${game.initialSpeed}") int initialSpeed) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.initialLength = initialLength;
        this.initialSpeed = initialSpeed;
        this.speed = initialSpeed;
        resetGame();
    }

    public void resetGame() {
        snake = new Snake(new Point(boardWidth / 2, boardHeight / 2), initialLength);
        generateApple();
        score = 0;
        speed = initialSpeed;
    }

    public void updateDirection(Direction direction) {
        snake.setDirection(direction);
    }

    public boolean updateGame() {
        snake.move();
        if (snake.getBody().getFirst().equals(apple.getPosition())) {
            score++;
            if (score > bestScore) {
                bestScore = score;
            }
            snake.grow();
            generateApple();
            increaseSpeed();
        }
        boolean collision = snake.checkCollision() || isOutOfBounds(snake.getBody().getFirst());
        if (collision) {
            resetGame();
        }
        return collision;
    }

    private void generateApple() {
        Point position;
        do {
            position = new Point(random.nextInt(boardWidth), random.nextInt(boardHeight));
        } while (snake.getBody().contains(position));
        apple = new Apple(position);
    }

    private boolean isOutOfBounds(Point point) {
        return point.getX() < 0 || point.getX() >= boardWidth || point.getY() < 0 || point.getY() >= boardHeight;
    }

    private void increaseSpeed() {
        int maxSpeedIncrease = (int) (initialSpeed * 0.3);
        if (initialSpeed - speed <= maxSpeedIncrease) {
            speed *= 0.98F;
        }
    }
}