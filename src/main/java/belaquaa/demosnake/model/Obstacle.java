package belaquaa.demosnake.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Obstacle {
    private final Point position;

    public Obstacle(Point position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstacle obstacle = (Obstacle) o;
        return Objects.equals(position, obstacle.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
