package chess;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static chess.Side.*;
import static chess.Piece.*;
import static chess.Empty.*;
import static chess.PieceType.*;
import static chess.MoveType.*;
import static chess.PawnPromChoice.*;

class Pawn extends Piece {
    Pawn(Side side, int x, int y, boolean moved) {
        super(side, x, y, moved);
        __value = 1;
        __pieceType = PAWNTYPE;
    }
    
    ArrayList<Move> legalMoves(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        ArrayList<Move> finalLegalMoves = new ArrayList<Move>();
        int x0 = __x;
        int y0 = __y;
        
        if (__side == WHITE) {
            int y1 = __y + 1;
            for (int x1 = __x-1; x1 <= __x+1; x1++) {
                Move move = new Move(x0, y0, x1, y1);
                if ((Board.inBounds(x1, y1)
                        && board.getPiece(x1, y1).side() == BLACK
                            && Math.abs(x1-x0)==1)
                    ||
                    (Board.inBounds(x1, y1)
                        && board.getPiece(x1, y1).pieceType() == EMPTYTYPE
                            && x1==x0)) {
                    legalMoves.add(move);
                }
            }
            if (!moved()
                    && board.getPiece(__x, __y+1).pieceType() == EMPTYTYPE
                        && board.getPiece(__x, __y+2).pieceType() == EMPTYTYPE) {
                legalMoves.add(new Move(__x, __y, __x, __y+2));
            }
            if (__x-1 >= 0
                && board.getPiece(__x-1, __y).pieceType() == PAWNTYPE
                    && board.getPiece(__x-1, __y).pawnMarked()
                        && board.getPiece(__x-1, __y+1).pieceType() == EMPTYTYPE) {
                legalMoves.add(new Move(__x, __y, __x-1, __y+1));
            }
            if (__x+1 <= 7
                && board.getPiece(__x+1, __y).pieceType() == PAWNTYPE
                    && board.getPiece(__x+1, __y).pawnMarked()
                        && board.getPiece(__x+1, __y+1).pieceType() == EMPTYTYPE) {
                legalMoves.add(new Move(__x, __y, __x+1, __y+1));
            }
            for (Move move : legalMoves) {
                if (move.getY1() == 7) {
                    int x1 = move.getX1();
                    finalLegalMoves.add(new Move(x0, y0, x1, 7, CHOICEROOK));
                    finalLegalMoves.add(new Move(x0, y0, x1, 7, CHOICEKNIGHT));
                    finalLegalMoves.add(new Move(x0, y0, x1, 7, CHOICEBISHOP));
                    finalLegalMoves.add(new Move(x0, y0, x1, 7, CHOICEQUEEN));
                } else {
                    finalLegalMoves.add(move);
                }
            }
        }
        
        if (__side == BLACK) {
            int y1 = __y - 1;
            for (int x1 = __x-1; x1 <= __x+1; x1++) {
                Move move = new Move(x0, y0, x1, y1);
                if (Board.inBounds(x1, y1)
                        && (board.getPiece(x1, y1).side() == WHITE
                            && Math.abs(x1-x0)==1)
                    ||
                    Board.inBounds(x1, y1)
                        &&(board.getPiece(x1, y1).pieceType() == EMPTYTYPE
                            && x1==x0)) {
                    legalMoves.add(move);
                }
            }
            if (!moved()
                    && board.getPiece(__x, __y-1).pieceType() == EMPTYTYPE
                        && board.getPiece(__x, __y-2).pieceType() == EMPTYTYPE) {
                legalMoves.add(new Move(__x, __y, __x, __y-2));
            }
            if (__x-1 >= 0
                && board.getPiece(__x-1, __y).pieceType() == PAWNTYPE
                    && board.getPiece(__x-1, __y).pawnMarked()
                        && board.getPiece(__x-1, __y-1).pieceType() == EMPTYTYPE) {
                legalMoves.add(new Move(__x, __y, __x-1, __y-1));
            }
            if (__x+1 <= 7
                && board.getPiece(__x+1, __y).pieceType() == PAWNTYPE
                    && board.getPiece(__x+1, __y).pawnMarked()
                        && board.getPiece(__x+1, __y-1).pieceType() == EMPTYTYPE) {
                legalMoves.add(new Move(__x, __y, __x+1, __y-1));
            }
            
            for (Move move : legalMoves) {
                if (move.getY1() == 0) {
                    int x1 = move.getX1();
                    finalLegalMoves.add(new Move(x0, y0, x1, 0, CHOICEROOK));
                    finalLegalMoves.add(new Move(x0, y0, x1, 0, CHOICEKNIGHT));
                    finalLegalMoves.add(new Move(x0, y0, x1, 0, CHOICEBISHOP));
                    finalLegalMoves.add(new Move(x0, y0, x1, 0, CHOICEQUEEN));
                } else {
                    finalLegalMoves.add(move);
                }
            }
        }
        return finalLegalMoves;
    }

    public String toString() {
        return __side.toString() + "P";
    }
    
    Piece copy() {
        return new Pawn(__side, __x, __y, __moved);
    }
    
    BufferedImage getImage() {
        BufferedImage img = null;
        try {
            if (__side == WHITE) {
                img = ImageIO.read(new File("chess/whitePawn.png"));
            } else if (__side == BLACK) {
                img = ImageIO.read(new File("chess/blackPawn.png"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return img;
    }
    
    static final Piece EMPTY = Piece.EMPTY;
}
    