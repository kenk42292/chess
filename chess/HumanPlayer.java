package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import static chess.MoveType.*;
import static chess.PieceType.*;
import static chess.Side.*;
import static chess.PawnPromChoice.*;

class HumanPlayer extends Player {
    public HumanPlayer(long initialTime, Side side, Board board) {
        super(initialTime, side, board);
    }
    
    Move makeMove() {
        long start = System.currentTimeMillis();
        int[] moveCommand = {-1, -1, -1, -1};
        JButton[][] buttons = __board.getButtons();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = __board.getPiece(i, j);
                __board.getButton(i, j).addActionListener(new PieceClickListener(i, j, moveCommand, __side));
            }
        }
        while (moveCommand[3] == -1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();
        long delTime = end - start;
        __timeRemaining -= delTime;
        Move move = new Move(moveCommand[0], moveCommand[1], moveCommand[2], moveCommand[3]);
        
        int pawnGoal = 7;
        if (__side == WHITE ) {
            pawnGoal = 7;
        } else if (__side == BLACK ) {
            pawnGoal = 0;
        }
        if (__board.getPiece(moveCommand[0], moveCommand[1]).pieceType() == PAWNTYPE
            && moveCommand[3] == pawnGoal) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            Container pane = frame.getContentPane();
            pane.setLayout(new GridLayout(9, 9));
            PawnPromChoice[] ppc = {CHOICEROOK, CHOICEKNIGHT, CHOICEBISHOP, CHOICEQUEEN};
            Piece[] samplePieces = {new Rook(__side, 0, 0, false),
                                            new Knight(__side, 0, 0, false),
                                                new Bishop(__side, 0, 0, false),
                                                    new Queen(__side, 0, 0, false)};
            PawnPromChoice[] choicePiece = {null};
            for (int i = 0; i < 4; i++) {
                JButton button = new JButton(new ImageIcon(samplePieces[i].getImage()));
                button.setPreferredSize(new Dimension(70, 70));
                pane.add(button);
                button.addActionListener(new PawnPromListener(frame, ppc[i], choicePiece));
            }
            frame.pack();
            frame.setVisible(true);
            while (choicePiece[0] == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
            PawnPromChoice piece = choicePiece[0];
            return new Move(moveCommand[0], moveCommand[1], moveCommand[2], moveCommand[3], piece);
        }
        ArrayList<Move> possibleMoves = findPlayerMoves(__board, __side);
        for (Move possibleMove : possibleMoves) {
            if (possibleMove.equals(move)) {
                return move;
            }
        }
        return makeMove();
    }    
    BufferedReader __reader;
}

