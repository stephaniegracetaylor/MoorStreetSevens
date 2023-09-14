import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("\n--*$****^--#*$***--.#*$!   M O O R   S T R E E T   S E V E N S   !$*#.--***$*#--^****$*--\n");

        // GAME
        Game game = new Game();


        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("NUMBER OF PLAYERS: ");
            try {
                game.setNumberOfPlayers(Integer.parseInt(scanner.nextLine().trim()));
            } catch (NumberFormatException exception) {
                // Loop
            }
        }
        while (game.getNumberOfPlayers() < 2);

        System.out.println();


        for (int player = 1; player <= game.getNumberOfPlayers(); player++) {
            Scanner scanner = new Scanner(System.in);
            System.out.printf("PLAYER %d: ", player);
            game.addPlayer(new Player(scanner.nextLine().toUpperCase()));
        }


        // BOARD
        Board board = new Board(game);


        while (!(game.isGameOver())) {
            if (game.getRound() == 0) {
                System.out.println(board.getBoard(game.getRound()));
                game.getNextRound();
            }

            // DEALS
            System.out.printf("DEAL BY %s FOR %d CARDS\n\n",
                    game.getPlayersForDeals().get(0).getName(),
                    game.getForbiddenBet(game.getRound()));

            // BETS
            for (Player player : game.getPlayersForBets()) {
                do {
                    Scanner scanner = new Scanner(System.in);
                    System.out.printf("BET FOR %s: ", player.getName());
                    try {
                        int bet = Integer.parseInt(scanner.nextLine());
                        if (game.isPlayerLastToBet(player)) {
                            if (game.getForbiddenBet(game.getRound())
                                    == bet + game.getBetsBeforePlayerLastToBet(game.getRound())) {
                                System.out.printf("    REQUIRED: UNDER-BETTING OR OVER-BETTING, "
                                                + "THAT IS, BETS CANNOT BE %d FOR THIS ROUND\n",
                                        game.getForbiddenBet(game.getRound()));
                            } else {
                                player.addBet(bet);
                            }
                        } else {
                            player.addBet(bet);
                        }
                    } catch (NumberFormatException exception) {
                        // Loop Again
                    }
                }
                while (player.getBets().size() < game.getRound());
            }

            System.out.println();

            // GETS
            do {
                for (Player player1 : game.getPlayersForGets()) {
                    do {
                        Scanner scanner = new Scanner(System.in);
                        System.out.printf("GET FOR %s: ", player1.getName());
                        try {
                            int get = Integer.parseInt(scanner.nextLine());
                            player1.addGet(get);
                            player1.addScore(game.getRound());
                        } catch (NumberFormatException exception) {
                            // Do Nothing
                        }
                    }
                    while (player1.getGets().size() < game.getRound());
                }

                int gets = 0;
                for (Player player2 : game.getPlayersForGets()) {
                    gets += player2.getGet(game.getRound());
                }
                if (gets != game.getForbiddenBet(game.getRound())) {
                    for (Player player3 : game.getPlayersForGets()) {
                        player3.removeGet(game.getRound());
                        player3.removeScore(game.getRound());
                    }
                    System.out.printf("    REQUIRED: GETS TO BE %d FOR THIS ROUND\n",
                            game.getForbiddenBet(game.getRound()));
                }
            }
            while (game.getPlayers().get(0).getGets().size() < game.getRound());

            System.out.println(board.getBoard(game.getRound()));
            game.getNextRound();
        }
    }
}