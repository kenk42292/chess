package chess;

import java.lang.InterruptedException;
import java.lang.Exception;

import static chess.Side.*;

class Game {
    public Game(Player player0, Player player1, Board board) {
        __player0 = player0;
        __player1 = player1;
        __board = board;
    }
    
    void play() {
        try {
            System.out.println(__board);
            long a = System.currentTimeMillis();
            __board.showGraphics();
            while (true) {
                if (stalemate(WHITE)) {
                    __stalemate = true;
                    break;
                }
                System.out.println("WHITE - TIME LEFT: " + (__player0.timeRemaining()/1000) + " sec");
                System.out.println("BLACK - TIME LEFT: " + (__player1.timeRemaining()/1000) + " sec");
                __player0.takeTurn();
                __board.showGraphics();
                System.out.println("\n\nTurn: " + __player1.side());
                System.out.println(__board);
                if (winner() != null) {
                    System.out.println("WHITE - TIME LEFT: " + (__player0.timeRemaining()/1000) + " sec");
                    System.out.println("BLACK - TIME LEFT: " + (__player1.timeRemaining()/1000) + " sec");
                    break;
                }
                // System.out.println("PLAYER1 MOVES: ");
                // System.out.println(Player.findPlayerMoves(__board, BLACK));
                if (stalemate(BLACK)) {
                    __stalemate = true;
                    break;
                }
                System.out.println("WHITE - TIME LEFT: " + (__player0.timeRemaining()/1000) + " sec");
                System.out.println("BLACK - TIME LEFT: " + (__player1.timeRemaining()/1000) + " sec");
                __player1.takeTurn();
                __board.showGraphics();
                System.out.println("\n\nTurn: " + __player0.side());
                System.out.println(__board);
                if (winner() != null) {
                    System.out.println("WHITE - TIME LEFT: " + (__player0.timeRemaining()/1000) + " sec");
                    System.out.println("BLACK - TIME LEFT: " + (__player1.timeRemaining()/1000) + " sec");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
   
    Side winner() {
        int whiteOptionNum = Player.findPlayerMoves(__board, WHITE).size();
        if (whiteOptionNum == 0
            && __board.kingChecked(Player.findSimplePlayerMoves(__board, BLACK), WHITE)) {
            return BLACK;
        }
        int blackOptionNum = Player.findPlayerMoves(__board, BLACK).size();
        if (blackOptionNum == 0
            && __board.kingChecked(Player.findSimplePlayerMoves(__board, WHITE), BLACK)) {
            return WHITE;
        }
        if (__player0.timeRemaining() <= 0) {
            return __player1.side();
        } else if (__player1.timeRemaining() <= 0) {
            return __player0.side();
        }
        return null;
    }
    
    boolean stalemate(Side side) {
        if (side == WHITE) {
            int whiteOptionNum = Player.findPlayerMoves(__board, WHITE).size();
            if (whiteOptionNum == 0
                && !__board.kingChecked(Player.findSimplePlayerMoves(__board, BLACK), WHITE)) {
                return true;
            }
        } else if (side == BLACK) {
            int blackOptionNum = Player.findPlayerMoves(__board, BLACK).size();
            if (blackOptionNum == 0
                && !__board.kingChecked(Player.findSimplePlayerMoves(__board, WHITE), BLACK)) {
                return true;
            }
        }
        return false;
    }
    
    Board __board;
    Player __player0;
    Player __player1;
    boolean __stalemate = false;
}
    