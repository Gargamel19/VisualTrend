package Gamemodes.ASTERN;

public class ScoreDummy {

    int x, y, score;
    Direction dir;

    public ScoreDummy(int x, int y, int score, Direction v) {
        this.dir = v;
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
