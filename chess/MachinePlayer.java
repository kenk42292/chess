package chess;

import java.util.ArrayList;
import java.util.Arrays;

class MachinePlayer extends Player {
    public MachinePlayer(long initialTime, Side side, Board board) {
        super(initialTime, side, board);
    }

    Move makeMove() {
        long start = System.currentTimeMillis();
        ArrayList<Move> possibleMoves = findPlayerMoves(__board, __side);
        Move[] bestMoves = new Move[4];
        double[] bestScores = new double[4];
        int listSize = possibleMoves.size() / 4;
        SubBrain subBrain0 = new SubBrain(__board, __side, possibleMoves, 0, listSize, bestMoves, bestScores);
        Thread t0 = new Thread(subBrain0);
        t0.start();
        SubBrain subBrain1 = new SubBrain(__board, __side, possibleMoves, 1, listSize, bestMoves, bestScores);
        Thread t1 = new Thread(subBrain1);
        t1.start();
        SubBrain subBrain2 = new SubBrain(__board, __side, possibleMoves, 2, listSize, bestMoves, bestScores);
        Thread t2 = new Thread(subBrain2);
        t2.start();
        SubBrain subBrain3 = new SubBrain(__board, __side, possibleMoves, 3, listSize, bestMoves, bestScores);
        Thread t3 = new Thread(subBrain3);
        t3.start();
        try {
            t0.join();  
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        Move bestMove = bestMoves[0];
        double bestScore = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 4; i++) {
            double score = bestScores[i];
            if (score > bestScore) {
                bestScore = score;
                bestMove = bestMoves[i];
            }
        }
        long end = System.currentTimeMillis();
        long delTime = end - start;
        __timeRemaining -= delTime;
        return bestMove;
    }
}




