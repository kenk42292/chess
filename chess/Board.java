package chess;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import static chess.Piece.*;
import static chess.Empty.*;
import static chess.Pawn.*;
import static chess.Side.*;
import static chess.PieceType.*;
import static chess.MoveType.*;

class Board extends JFrame {
    public Board(Side perspective) {
        __grid = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                __grid[i][j] = INITIAL_GRID[i][j].copy();
            }
        }
        __perspective = perspective;
    }
    
    public Board(Piece[][] grid) {
        __grid = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                __grid[i][j] = grid[i][j].copy();
            }
        }
    }
    
    void executeMove(Move move) {
        Side movingSide = getPiece(move.getX0(), move.getY0()).side();
        MoveType moveType = move.moveType(this);
        if (moveType == TWOSTEP) {
            getPiece(move.getX0(), move.getY0()).pawnMark(true);
            movePiece(move);
        } else if (moveType == ENPASSANTE) {
            unmarkPieces(Side.opponent(movingSide));
            movePiece(move);
            setPiece(move.getX1(), move.getY0(), EMPTY);
        } else if (moveType == CASTLE) {
            unmarkPieces(Side.opponent(movingSide));
            movePiece(move);
            int rookX0 = (move.getX1()-move.getX0()>0) ? 7 : 0;
            int rookX1 = (move.getX1()-move.getX0()>0) ? 5 : 3;
            movePiece(new Move(rookX0, move.getY0(), rookX1, move.getY1()));
        } else if (moveType == PAWNPROM) {
            movePiece(move);
            Piece p = new Queen(movingSide, move.getX1(), move.getY1(), false);
            switch (move.pawnPromChoice()) {
                case CHOICEROOK:
                    p = new Rook(movingSide, move.getX1(), move.getY1(), false);
                    break;
                case CHOICEKNIGHT:
                    p = new Knight(movingSide, move.getX1(), move.getY1(), false);
                    break;
                case CHOICEBISHOP:
                    p = new Bishop(movingSide, move.getX1(), move.getY1(), false);
                    break;
                case CHOICEQUEEN:
                    p = new Queen(movingSide, move.getX1(), move.getY1(), false);
                    break;
                default:
                    p = new Queen(movingSide, move.getX1(), move.getY1(), false);
                    break;
            }
            setPiece(move.getX1(), move.getY1(), p);
        } else {
            unmarkPieces(Side.opponent(movingSide));
            movePiece(move);
        }
    }

    void movePiece(Move move) {
        Piece p = getPiece(move.getX0(), move.getY0());
        setPiece(move.getX1(), move.getY1(), p);
        setPiece(move.getX0(), move.getY0(), EMPTY);
        p.setLocation(move.getX1(), move.getY1());
        p.setMoved();
    }
    
    void unmarkPieces(Side side) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0;  j < 8; j++) {
                Piece p = getPiece(i, j);
                if (p.side() == side && p.pieceType() == PAWNTYPE) {
                    p.pawnMark(false);
                }
            }
        }
    }
        
    Piece getPiece(int x, int y) {
        return __grid[7-y][x];
    }
        
    void setPiece(int x, int y, Piece piece) {
        __grid[7-y][x] = piece;
    }
    
    JButton getButton(int x, int y){
        return __buttons[7-y][x];
    }
    
    static boolean inBounds(int x, int y) {
        if (x < 0 || x > 7) return false;
        if (y < 0 || y > 7) return false;
        return true;
    }
    
    boolean emptyBtwnHoriz(int x0, int x1, int y) {
        int delX = (x1-x0)/Math.abs(x1-x0);
        int x = x0+delX;
        while (x != x1) {
            Piece p = getPiece(x, y);
            if (p.pieceType() != EMPTYTYPE) return false;
            x += delX;
        }
        return true;
    }
    
    boolean noThreatHoriz(int x0, int x1, int y, Side side) {
        ArrayList<Move> opponentMoves = new ArrayList<Move>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = getPiece(i, j);
                if (p.side() == Side.opponent(side)) {
                    if ((p.pieceType() == KINGTYPE && p.moved())
                        || p.pieceType() != KINGTYPE) {
                        opponentMoves.addAll(p.legalMoves(this));
                    }
                }
            }
        }
        int delX = (x1-x0)/Math.abs(x1-x0);
        int x = x0;
        while (x != x1+delX) {
            if (threatened(opponentMoves, x, y)) return false;
            x += delX;
        }
        return true;
    }
    
    boolean kingChecked(ArrayList<Move> opponentMoves, Side side) {
        int kingX = 0;
        int kingY = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = getPiece(i, j);
                if (p.pieceType() == KINGTYPE && p.side() == side) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
        }
        return threatened(opponentMoves, kingX, kingY);
    }
    
    boolean threatened(ArrayList<Move> opponentMoves, int x, int y) {
        for (Move opponentMove : opponentMoves) {
            if (x == opponentMove.getX1() && y == opponentMove.getY1()) {
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        String stringRep = "";
        for (int i = 7; i >= 0; i--) {
            Piece[] row = __grid[7-i];
            stringRep += i + " || " + Arrays.toString(row) + "\n\n";
        }
        stringRep += "      ==  ==  ==  ==  ==  ==  ==  ==  \n";
        stringRep += "      0   1   2   3   4   5   6   7   \n";
        return stringRep;
    }
    
    static double evalMax(Board board, Side side, double alpha, double beta, int depth) {
        if (depth == 0) {
            return heuristic(board, side);
        }
        ArrayList<Move> possibleMoves = Player.findPlayerMoves(board, side);
        if (possibleMoves.size() == 0) {
            return heuristic(board, side);
        }
        double maxScore = Double.NEGATIVE_INFINITY;
        double score;
        for (Move move : possibleMoves) {
            Board boardCopy = board.copy();
            boardCopy.executeMove(move);
            score = evalMin(boardCopy, side, alpha, beta, depth-1);
            if (score > alpha) {
                alpha = score;
            }
            if (score > beta) {
                return score;
            }
            if (score > maxScore) {
                maxScore = score;
            }
        }
        return maxScore;
    }
    
    static double evalMin(Board board, Side side, double alpha, double beta, int depth) {
        if (depth == 0) {
            return heuristic(board, side);
        }
        ArrayList<Move> possibleMoves = Player.findPlayerMoves(board, Side.opponent(side));
        if (possibleMoves.size() == 0) {
            return heuristic(board, side);
        }
        double minScore = Double.POSITIVE_INFINITY;
        double score;
        for (Move move : possibleMoves) {
            Board boardCopy = board.copy();
            boardCopy.executeMove(move);
            score = evalMax(boardCopy, side, alpha, beta, depth-1);
            if (score < beta) {
                beta = score;
            }
            if (score < alpha) {
                return score;
            }
            if (score < minScore) {
                minScore = score;
            }
        }
        return minScore;
    }
                
    
    
    static double heuristic(Board board, Side side) {
        double selfPieceScore = 0;
        double opponentPieceScore = 0;
        double degFreedom = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.getPiece(i, j);
                if (p.side() == side) {
                    selfPieceScore += p.value();
                    degFreedom += p.legalMoves(board).size();
                } else if (p.side() == Side.opponent(side)) {
                    opponentPieceScore += p.value();
                }
            }
        }
        return selfPieceScore - opponentPieceScore + degFreedom/10;
    }
    
    
    static final Piece EMPTY = Piece.EMPTY;
        
    static final Piece[][] INITIAL_GRID =
    {
        {new Rook(BLACK, 0, 7, false), new Knight(BLACK, 1, 7, false), new Bishop(BLACK, 2, 7, false), new Queen(BLACK, 3, 7, false), new King(BLACK, 4, 7, false), new Bishop(BLACK, 5, 7, false), new Knight(BLACK, 6, 7, false), new Rook(BLACK, 7, 7, false)},
        {new Pawn(BLACK, 0, 6, false), new Pawn(BLACK, 1, 6, false), new Pawn(BLACK, 2, 6, false), new Pawn(BLACK, 3, 6, false), new Pawn(BLACK, 4, 6, false), new Pawn(BLACK, 5, 6, false), new Pawn(BLACK, 6, 6, false), new Pawn(BLACK, 7, 6, false)},
        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        {new Pawn(WHITE, 0, 1, false), new Pawn(WHITE, 1, 1, false), new Pawn(WHITE, 2, 1, false), new Pawn(WHITE, 3, 1, false), new Pawn(WHITE, 4, 1, false), new Pawn(WHITE, 5, 1, false), new Pawn(WHITE, 6, 1, false), new Pawn(WHITE, 7, 1, false)},
        {new Rook(WHITE, 0, 0, false), new Knight(WHITE, 1, 0, false), new Bishop(WHITE, 2, 0, false), new Queen(WHITE, 3, 0, false), new King(WHITE, 4, 0, false), new Bishop(WHITE, 5, 0, false), new Knight(WHITE, 6, 0, false), new Rook(WHITE, 7, 0, false)}
    };
        
    Board copy() {
        return new Board(__grid);
    }
    
    void showGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = getContentPane();
        pane.removeAll();
        pane.setLayout(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            Color color0 = Color.GRAY;
            Color color1 = Color.WHITE;
            if (i%2==0) {
                color0 = Color.WHITE;
                color1 = Color.GRAY;
            }
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(70, 70));
                if (i == 8 && j == 0) {
                    pane.add(button);
                    continue;
                }
                if (__perspective == BLACK) {
                    if (j == 0) {
                        button = new JButton(Integer.toString(i+1));
                        pane.add(button);
                        continue;
                    }
                    if (i == 8) {
                        button = new JButton(""+ ((char) (73 - j)));
                        pane.add(button);
                        continue;
                    }
                    if (i != 8 && j != 0) {
                        BufferedImage img = getPiece(8-j, i).getImage();
                        if (img != null) {
                            button.setIcon(new ImageIcon(img));
                        }
                    }
                }
                if (__perspective == WHITE) {
                    if (j == 0) {
                        button = new JButton(Integer.toString(8-i));
                        pane.add(button);
                        continue;
                    }
                    if (i == 8) {
                        button = new JButton(""+ ((char) (64 + j)));
                        pane.add(button);
                        continue;
                    }
                    if (i != 8 && j != 0) {
                        BufferedImage img = getPiece(j-1, 7-i).getImage();
                        if (img != null) {
                            button.setIcon(new ImageIcon(img));
                        }
                    }
                }
                if (j%2==0) {
                    button.setBackground(color1);
                } else {
                    button.setBackground(color0);
                }
                if (i != 8 && j != 0) {
                    __buttons[i][j-1] = button;
                }
                pane.add(button);
            }
        }
        pack();
        setVisible(true);
    }
    JButton[][] getButtons() {
        return __buttons;
    }
    
    private Piece[][] __grid;
    static double gamma = 0.9;
    int[] __moveCommand = new int[4];
    JButton[][] __buttons = new JButton[8][8];
    Side __perspective;
}



