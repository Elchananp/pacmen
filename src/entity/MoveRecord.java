package entity;

public class MoveRecord {
    private String direction;
    private int steps;

    public MoveRecord(String direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    public String getDirection() {
        return direction;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Move{" +
                "direction='" + direction + '\'' +
                ", steps=" + steps +
                '}';
    }
}

