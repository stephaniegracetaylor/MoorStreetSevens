import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int SEVENS = 7;
    public static final int BONUS = 3;

    private int round;
    private int numberOfRounds;

    private int numberOfPlayers;
    private List<Player> players;
    private List<Player> playersForDeals;
    private List<Player> playersForBets;
    private List<Player> playersForGets;

    private List<Integer> forbiddenBets;

    Game() {
        round = 0;
        numberOfRounds = SEVENS * 2 - 1;
        numberOfPlayers = 0;
        players = new ArrayList<>();
        getForbiddenBets();
    }

    public int getRound() {
        return round;
    }

    public void getNextRound() {
        round++;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public int getPlayerLongestName() {
        int playerLongestName = 0;

        for (Player player : players) {
            playerLongestName = Math.max(player.getName().length(),
                    playerLongestName);
        }
        return playerLongestName;
    }

    public List<Player> getPlayersForDeals() {
        if (getRound() == 1) {
            playersForDeals = new ArrayList<>(players);
        } else {
            Player playerForDeals = playersForDeals.get(0);
            playersForDeals.remove(playerForDeals);
            playersForDeals.add(playerForDeals);
        }
        return playersForDeals;
    }

    public List<Player> getPlayersForBets() {
        if (getRound() == 1) {
            playersForBets = new ArrayList<>(players);
        }

        Player playerForBets = playersForBets.get(0);
        playersForBets.remove(playerForBets);
        playersForBets.add(playerForBets);

        return playersForBets;
    }

    public List<Player> getPlayersForGets() {
        return playersForGets = playersForBets;
    }

    public void getForbiddenBets() {
        int forbiddenBet = SEVENS;
        forbiddenBets = new ArrayList<>();
        for (int i = 0; i < numberOfRounds; i++) {
            if (i < numberOfRounds / 2) {
                forbiddenBets.add(forbiddenBet--);
            } else {
                forbiddenBets.add(forbiddenBet++);
            }
        }
    }

    public int getForbiddenBet(int round) {
        return forbiddenBets.get(round - 1);
    }

    public int getBetsBeforePlayerLastToBet(int round) {
        int betsBeforePlayerLastToBet = 0;
        for (Player player : playersForBets) {
            if (!isPlayerLastToBet(player)) {
                betsBeforePlayerLastToBet += player.getBet(round);
            }
        }
        return betsBeforePlayerLastToBet;
    }

    public boolean isPlayerLastToBet(Player player) {
        return player == playersForBets.get(playersForBets.size() - 1);
    }

    public boolean isGameOver() {
        if (round > numberOfRounds) {
            getGameOverMessage();
        }
        return round > numberOfRounds;
    }

    private int getWinningScore() {
        int winningScore = 0;
        for (Player player : players) {
            winningScore = Math.max(player.getScore(numberOfRounds),
                    winningScore);
        }
        return winningScore;
    }

    private List<String> getWinnerOrWinners() {
        List<String> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getScore(numberOfRounds) == getWinningScore()) {
                winners.add(player.getName());
            }
        }
        return winners;
    }

    private boolean isWinnerOrWinners() {
        return getWinnerOrWinners().size() > 1;
    }

    private boolean isPlayerSecondLastWinner(String winner) {
        if (isWinnerOrWinners()) {
            return winner.equals(
                    getWinnerOrWinners()
                            .get(getWinnerOrWinners().size() - 2));
        }
        return false;
    }

    private boolean isPlayerLastWinner(String winner) {
        if (isWinnerOrWinners()) {
            return winner.equals(
                    getWinnerOrWinners()
                            .get(getWinnerOrWinners().size() - 1));
        }
        return false;
    }

    private void getGameOverMessage() {
        StringBuilder winners = new StringBuilder();

        for (String winner : getWinnerOrWinners()) {
            winners.append(winner);
            if (isWinnerOrWinners()) {
                if (isPlayerSecondLastWinner(winner)) {
                    winners.append(" AND ");
                } else if (!isPlayerLastWinner(winner))
                    winners.append(", ");
                }
            }

        System.out.printf("CONGRATULATIONS, %s !! ", winners);

        if (isWinnerOrWinners()) {
            System.out.print("WINNERS WINNERS CHICKEN DINNERS !!");
        } else {
            System.out.print("WINNER WINNER CHICKEN DINNER !!");
        }

        System.out.print("\n\n\nGAME OVER\n\n\n");
    }
}
