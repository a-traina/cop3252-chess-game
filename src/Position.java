public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        else if(!(o instanceof Position)) {
            return false;
        }

        Position p = (Position) o;

        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        return x + 13 * y;
    }
}
