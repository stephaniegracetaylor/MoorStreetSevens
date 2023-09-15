import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;

    List<Integer> bets;
    List<Integer> gets;
    List<Integer> scores;

    Player(String name) {
        this.name = name;

        bets = new ArrayList<>();
        gets = new ArrayList<>();
        scores = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBets() {
        return bets;
    }

    public List<Integer> getGets() {
        return gets;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public int getBet(int round) {
        return bets.get(round - 1);
    }

    public int getGet(int round) {
        return gets.get(round - 1);
    }

    public int getScore(int round) {
        int score = 0;
        for (int i = 0; i < round; i++) {
            score += scores.get(i);
        }
        return score;
    }

    public void addBet(int bet) {
        bets.add(bet);
    }

    public void addGet(int get) {
        gets.add(get);
    }

    public void addScore(int round) {
        if (getBet(round) == getGet(round)) {
            scores.add(getGet(round) + Game.BONUS);
        } else {
            scores.add(getGet(round));
        }
    }

    public void removeGet(int round) {
        gets.remove(round - 1);
    }

    public void removeScore(int round) {
        scores.remove(round - 1);
    }
}
