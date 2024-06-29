package belaquaa.demosnake.model;

import belaquaa.demosnake.configuration.Direction;
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
        Point newHead = getNewHeadPosition();
        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        body.addFirst(getNewHeadPosition());
    }

    public Point getHead() {
        return body.getFirst();
    }

    private Point getNewHeadPosition() {
        Point head = body.getFirst();
        return switch (direction) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
        };
    }

    public boolean checkCollision() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }
}
