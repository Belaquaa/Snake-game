package belaquaa.demosnake.model;

import belaquaa.demosnake.enums.Direction;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
public class Snake {
    private final LinkedList<Point> body;
    @Setter
    private Direction direction;

    public Snake(Point initialPosition, int initialLength) {
        body = new LinkedList<>();
        for (int i = 0; i < initialLength; i++) {
            body.add(new Point(initialPosition.x() - i, initialPosition.y()));
        }
        direction = Direction.RIGHT;
    }

    public void move() {
        body.addFirst(getNewHeadPosition());
        body.removeLast();
    }

    public void grow() {
        body.addFirst(getNewHeadPosition());
    }

    public Point getHead() {
        return body.getFirst();
    }

    public boolean checkCollision() {
        Point head = getHead();
        return body.stream().skip(1).anyMatch(head::equals);
    }

    private Point getNewHeadPosition() {
        Point head = getHead();
        return switch (direction) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
        };
    }
}
