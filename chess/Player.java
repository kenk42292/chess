package chess;

import java.util.ArrayList;

abstract class Player {
    public Player(long initialTime, Side side, Board board) {
        __timeRemaining = initialTime;
        __side = side;
        __board = board;
    }
    
    void takeTurn() {
        Move move = makeMove();
        __board.executeMove(move);
    }
    
    static ArrayList<Move> findPlayerMoves(Board board, Side side) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board.getPiece(x, y);
                if (p.side() != side) continue;
                for (Move move : p.legalMoves(board)) {
                    Board boardCopy = board.copy();
                    boardCopy.executeMove(move);
                    ArrayList<Move> opponentMoves
                        = findSimplePlayerMoves(boardCopy, Side.opponent(side));
                    if (!boardCopy.kingChecked(opponentMoves, side)) {
                        possibleMoves.add(move);
                    }
                }
            }
        }
        return possibleMoves;
    }
    
    static ArrayList<Move> findSimplePlayerMoves(Board board, Side side) {
        ArrayList<Move> playerMoves = new ArrayList<Move>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board.getPiece(x, y);
                if (p.side() == side) {
                    playerMoves.addAll(p.legalMoves(board));
                }
            }
        }
        return playerMoves;
    }
    
    long timeRemaining() {
        return __timeRemaining;
    }
    
    Side side() {
        return __side;
    }
    
    abstract Move makeMove();
    
    long __timeRemaining;
    Side __side;
    Board __board;
}