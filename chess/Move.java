package chess;

import static chess.MoveType.*;
import static chess.PieceType.*;
import static chess.Side.*;

class Move {
    public Move(int x0, int y0, int x1, int y1) {
        __x0 = x0;
        __y0 = y0;
        __x1 = x1;
        __y1 = y1;
    }
    
    public Move(int x0, int y0, int x1, int y1, PawnPromChoice pawnPromChoice) {
        __x0 = x0;
        __y0 = y0;
        __x1 = x1;
        __y1 = y1;
        __pawnPromChoice = pawnPromChoice;
    }
    
    int getX0() {
        return __x0;
    }
    
    int getY0() {
        return __y0;
    }
    
    int getX1() {
        return __x1;
    }
    
    int getY1() {
        return __y1;
    }
    
    public String toString() {
        return "(" + __x0 + ", " + __y0 + ") --> (" + __x1 + ", " + __y1 + ")";
    }
    
    MoveType moveType(Board board) {
        Piece p = board.getPiece(getX0(), getY0());
        if (p.pieceType() == KINGTYPE
            && Math.abs(getX1()-getX0()) == 2) {
            return CASTLE;
        } else if (p.pieceType() == PAWNTYPE) {
            if (Math.abs(getY1()-getY0()) == 2) {
                return TWOSTEP;
            }
            if (Math.abs(getY1()-getY0()) == 1
                && Math.abs(getX1()-getX0()) == 1
                    && board.getPiece(getX1(), getY1()).pieceType() == EMPTYTYPE) {
                return ENPASSANTE;
            }
            if ((getY1() == 7 && p.side() == WHITE)
                || (getY1() == 0 && p.side() == BLACK)) {
                return PAWNPROM;
            }
        }
        return NORMAL;
    }
    
    boolean equals(Move move) {
        if (getX0() == move.getX0()
            && getY0() == move.getY0()
                && getX1() == move.getX1()
                    && getY1() == move.getY1()) {
            return true;
        } else {
            return false;
        }
    }
    
    PawnPromChoice pawnPromChoice() {
        return __pawnPromChoice;
    }
    
    int __x0;
    int __y0;
    int __x1;
    int __y1;
    PawnPromChoice __pawnPromChoice = null;
}
