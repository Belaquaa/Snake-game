package belaquaa.demosnake.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
public class Snake {
    private LinkedList<Point> body;
    @Setter
    private Direction direction;

    public Snake(Point initialPosition, int initialLength) {
        body = new LinkedList<>();
        for (int i = 0; i < initialLength; i++) {
            body.add(new Point(initialPosition.getX() - i, initialPosition.getY()));
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

    private Point getNewHeadPosition() {
        Point head = body.getFirst();
        return switch (direction) {
            case UP -> new Point(head.getX(), head.getY() - 1);
            case DOWN -> new Point(head.getX(), head.getY() + 1);
            case LEFT -> new Point(head.getX() - 1, head.getY());
            case RIGHT -> new Point(head.getX() + 1, head.getY());
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
