package chess;

import java.util.Arrays;

import static chess.Side.*;

class Main {
    public static void main(String[] args) {
        Board board;
        Player player0;
        Player player1;
        long timeLimit = 600000;
        if (Arrays.asList(args).contains("-black")) {
            board = new Board(BLACK);
            player0 = new MachinePlayer(timeLimit, WHITE, board);
            player1 = new HumanPlayer(timeLimit, BLACK, board);
        } else {
            board = new Board(WHITE);
            player0 = new HumanPlayer(timeLimit, WHITE, board);
            player1 = new MachinePlayer(timeLimit, BLACK, board);
        }
        Game game = new Game(player0, player1, board);
        game.play();
        announceWinner(game);
        System.exit(0);
    }
    static void announceWinner(Game game) {
        if (game.__stalemate) {
            System.out.println("stalemate");
        } else {
            System.out.println("winner: " + game.winner().toString());
        }
    }
}


