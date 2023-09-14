public class Board {
    private Game game;
    private String board;
    private static int BAY;
    private static final String ROUND = "ROUND";
    private static final String DEAL = "DEAL";
    private static final String CARDS = "CARDS";

    Board(Game game) {
        this.game = game;
        board = "\n";
        BAY = Math.max(10, game.getPlayerLongestName() + 2);
    }

    public String getBoard(int round) {
        return board += getBoardPerRound(round);
    }

    private String getBoardPerRound(int round) {
        StringBuilder board = new StringBuilder();

        if (round == 0) {
            board.append(boardRoundAndPlayers());
            board.append(boardDottedLine());
        } else {
            board.append(boardBets(round));
            board.append(boardGets(round));
            board.append(boardScores(round));
            board.append(boardDottedLine());
        }
        return board.toString();
    }

    private String spaces(int limit) {
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            board.append(" ");
        }
        return board.toString();
    }

    private String boardRoundAndPlayers() {
        StringBuilder board = new StringBuilder();

        board.append(" | ");
        board.append(ROUND);
        board.append(spaces(BAY - ROUND.length()));
        board.append(" | ");

        for (Player player : game.getPlayers()) {
            board.append(player.getName());
            board.append(spaces(BAY - player.getName().length()));
            board.append(" | ");
        }
        board.append("\n");

        return board.toString();
    }

    private String boardDottedLine() {
        StringBuilder board = new StringBuilder();

        board.append(" ");
        int line = (game.getNumberOfPlayers() + 1) * (BAY + 3);

        for (int i = 0; i <= line; i++) {
            board.append("-");
        }
        board.append("\n");
        return board.toString();
    }

    private String boardBets(int round) {
        StringBuilder board = new StringBuilder();

        board.append(" | ");
        board.append(spaces(BAY - 2));
        if (game.getRound() < 10) {
            board.append(" ");
            board.append(game.getRound());
        } else {
            board.append(game.getRound());
        }
        board.append(" | ");

        for (Player player : game.getPlayers()) {
            board.append("Bet: ");
            board.append(player.getBet(round));
            board.append(spaces(BAY - 6));
            board.append(" | ");
        }
        board.append("\n");

        return board.toString();
    }

    private String boardGets(int round) {
        StringBuilder board = new StringBuilder();

        board.append(" | ");
        board.append(spaces(BAY));
        board.append(" | ");

        for (Player player : game.getPlayers()) {
            board.append("Get: ");
            board.append(player.getGet(round));
            board.append(spaces(BAY - 6));
            board.append(" | ");
        }
        board.append("\n");

        return board.toString();
    }

    private String boardScores(int round) {
        StringBuilder board = new StringBuilder();

        board.append(" | ");
        board.append(spaces(BAY - 6));
        board.append("Score:");
        board.append(" | ");

        for (Player player : game.getPlayers()) {
            board.append(spaces(BAY - 2));

            if (player.getScore(round) < 10) {
                board.append(" ");
                board.append(player.getScore(round));
            } else {
                board.append(player.getScore(round));
            }

            board.append(" | ");
        }
        board.append("\n");

        return board.toString();
    }
}
