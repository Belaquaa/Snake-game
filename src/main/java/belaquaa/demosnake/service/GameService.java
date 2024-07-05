package belaquaa.demosnake.service;

import belaquaa.demosnake.configuration.GameConfig;
import belaquaa.demosnake.configuration.GameState;
import belaquaa.demosnake.configuration.Score;
import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.enums.Direction;
import belaquaa.demosnake.model.Obstacle;
import belaquaa.demosnake.model.Snake;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import belaquaa.demosnake.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Getter
public class GameService {
    private Snake snake;
    private Apple apple;
    private List<Obstacle> obstacles;
    private Direction direction = Direction.RIGHT;

    @Value("${board.width}")
    private int boardWidth;

    @Value("${board.height}")
    private int boardHeight;

    @Value("${snake.initialLength}")
    private int initialLength;

    @Value("${snake.initialSpeed}")
    private int initialSpeed;

    @Value("${obstacle.numberOfObstacles}")
    private int numberOfObstacles;

    private final Random random = new Random();
    private float speed;
    private int score;
    private int bestScore;

    @PostConstruct
    public void init() {
        resetGame();
    }

    public void resetGame() {
        snake = new Snake(new Point(boardWidth / 2, boardHeight / 2), initialLength);
        apple = new Apple(new Point(boardWidth / 2 + Math.min(boardWidth / 4, 3), boardHeight / 2), true);
        generateObstacles();
        speed = initialSpeed;
        score = 0;
    }

    public boolean updateGame() {
        moveSnake();
        if (snake.checkCollision() || isOutOfBounds(snake.getHead()) || checkObstacleCollision()) {
            resetGame();
            return true;
        }
        return false;
    }

    public boolean checkObstacleCollision() {
        Point head = snake.getHead();
        return obstacles != null && obstacles.stream().anyMatch(obstacle -> obstacle.position().equals(head));
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
            score += apple.isGolden() ? 5 : 1;
            bestScore = Math.max(score, bestScore);
            speed *= apple.isGolden() ? 1.03F : 0.99F;
            snake.grow();
            generateApple();
        }
    }

    private boolean isPositionOccupied(Point position) {
        return snake.getBody().contains(position) ||
                (obstacles != null && obstacles.stream().anyMatch(obstacle -> obstacle.position().equals(position)));
    }


    private void generateApple() {
        Point position;
        do {
            position = new Point(random.nextInt(boardWidth - 2) + 1, random.nextInt(boardHeight - 2) + 1);
        } while (isPositionOccupied(position));

        apple = new Apple(position, random.nextInt(10) == 1);
    }


    private void generateObstacles() {
        obstacles = new ArrayList<>();
        for (int i = 0; i < numberOfObstacles; i++) {
            Point position;
            do {
                position = new Point(random.nextInt(boardWidth - 2) + 1, random.nextInt(boardHeight - 2) + 1);
            } while (isPositionOccupied(position));
            obstacles.add(new Obstacle(position));
        }
    }

    public GameState getGameState() {
        return new GameState(snake, apple, obstacles);
    }

    public GameConfig getGameConfig() {
        return new GameConfig(boardWidth, boardHeight, speed);
    }

    public Score getScore() {
        return new Score(score, bestScore);
    }
}
