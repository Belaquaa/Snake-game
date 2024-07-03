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
        apple = new Apple(new Point(boardWidth / 2 + boardWidth / 4, boardHeight / 2), true);
        generateObstacles();
        speed = initialSpeed;
        score = 0;
    }

    public boolean updateGame() {
        moveSnake();
        boolean collision = snake.checkCollision() || isOutOfBounds(snake.getBody().getFirst()) || checkObstacleCollision();
        if (collision) {
            resetGame();
        }
        return collision;
    }

    public boolean checkObstacleCollision() {
        Point head = snake.getHead();
        return obstacles.stream().anyMatch(obstacle -> obstacle.getPosition().equals(head));
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
            if (apple.isGolden()) {
                score += 5;
                speed *= 1.03F;
            } else {
                score++;
            }
            bestScore = Math.max(score, bestScore);
            snake.grow();
            generateApple();
            increaseSpeed();
        }
    }

    private boolean isPositionOccupied(Point position) {
        return snake.getBody().contains(position) ||
                obstacles.stream().anyMatch(obstacle -> obstacle.getPosition().equals(position));
    }

    private void generateApple() {
        Point position;
        do {
            position = new Point(random.nextInt(boardWidth - 2) + 1, random.nextInt(boardHeight - 2) + 1);
        } while (isPositionOccupied(position));

        boolean isGolden = random.nextInt(10) == 1;  // 10% chance for a golden apple
        apple = new Apple(position, isGolden);
    }


    private void generateObstacles() {
        obstacles = new ArrayList<>();
        for (int i = 0; i < numberOfObstacles; i++) {
            Point position;
            do {
                position = new Point(random.nextInt(boardWidth - 2) + 1, random.nextInt(boardHeight - 2) + 1);
            } while (snake.getBody().contains(position) || obstacles.contains(new Obstacle(position)));
            obstacles.add(new Obstacle(position));
        }
    }

    private void increaseSpeed() {
        speed *= 0.99F;
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
