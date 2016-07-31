package chess;

import java.util.ArrayList;
import java.util.List;
import java.lang.Runnable;

class SubBrain implements Runnable {
    SubBrain(Board board, Side side, ArrayList<Move> possibleMoves, int index, int listSize, Move[] bestMoves, double[] bestScores) {
        __board = board;
        __side = side;
        __possibleMoves = possibleMoves;
        __index = index;
        __listSize = listSize;
        __bestMoves = bestMoves;
        __bestScores = bestScores;
    }
    
    public void run() {
        List<Move> subList = __possibleMoves.subList(__index*__listSize, (__index+1)*__listSize);
        Move bestMove = (subList.size() == 0) ? null : subList.get(0);
        double bestScore = Double.NEGATIVE_INFINITY;
        for (Move move : subList) {
            Board boardCopy = __board.copy();
            boardCopy.executeMove(move);
            double score = Board.evalMin(boardCopy, __side, bestScore, Double.POSITIVE_INFINITY, 3);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        __bestScores[__index] = bestScore;
        __bestMoves[__index] = bestMove;
    }

    Board __board;
    Side __side;
    ArrayList<Move> __possibleMoves;
    int __index;
    int __listSize;
    Move[] __bestMoves;
    double[] __bestScores;
}

