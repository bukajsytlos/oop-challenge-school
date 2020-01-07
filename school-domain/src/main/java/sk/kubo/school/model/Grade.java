package sk.kubo.school.model;

public enum Grade {
    _1(1),
    _2(2),
    _3(3),
    _4(4),
    _5(5);

    int score;

    Grade(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
