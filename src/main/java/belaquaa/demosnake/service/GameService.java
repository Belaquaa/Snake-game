package belaquaa.demosnake.service;

import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.configuration.Direction;
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
    private final Direction direction = Direction.RIGHT;
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
                       @Value("${game.speed}") int initialSpeed) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.initialLength = initialLength;
        this.initialSpeed = initialSpeed;
        resetGame();
    }

    public void resetGame() {
        snake = new Snake(new Point(boardWidth / 2, boardHeight / 2), initialLength);
        apple = new Apple(new Point(boardWidth / 2 + boardWidth / 4, boardHeight / 2));
        score = 0;
        speed = initialSpeed;
    }

    public boolean updateGame() {
        moveSnake();
        boolean collision = snake.checkCollision() || isOutOfBounds(snake.getBody().getFirst());
        if (collision) {
            resetGame();
        }
        return collision;
    }

    public void updateDirection(Direction direction) {
        Direction currentDirection = snake.getDirection();
        boolean isOpposite = (direction == Direction.UP && currentDirection == Direction.DOWN) ||
                (direction == Direction.DOWN && currentDirection == Direction.UP) ||
                (direction == Direction.LEFT && currentDirection == Direction.RIGHT) ||
                (direction == Direction.RIGHT && currentDirection == Direction.LEFT);

        if (!isOpposite) {
            snake.setDirection(direction);
        }
    }

    public boolean isOutOfBounds(Point point) {
        return point.x() < 0 || point.x() >= boardWidth || point.y() < 0 || point.y() >= boardHeight;
    }

    public void moveSnake() {
        snake.move();
        if (snake.getHead().equals(apple.position())) {
            score++;
            bestScore = Math.max(score, bestScore);
            snake.grow();
            generateApple();
            increaseSpeed();
        }
    }

    private void generateApple() {
        Point position;
        do {
            position = new Point(random.nextInt(boardWidth - 2) + 1, random.nextInt(boardHeight - 2) + 1);
        } while (snake.getBody().contains(position));
        apple = new Apple(position);
    }

    private void increaseSpeed() {
        speed *= 0.99F;
    }
}