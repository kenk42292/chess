package chess;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import static chess.Empty.*;
import static chess.PieceType.*;
import static chess.MoveType.*;

abstract class Piece {
    Piece(Side side, int x, int y, boolean moved) {
        __side = side;
        __x = x;
        __y = y;
        __moved = moved;
    }
    
    Side side() {
        return __side;
    }
    
    ArrayList<Move> vectorMoves(int delX, int delY, Board board) {
    /**Given a unit vector for direction, board, and side returns all legal moves from piece's position to indicated direction*/
        ArrayList<Move> vectorMoves = new ArrayList<Move>();
        int x = __x + delX;
        int y = __y + delY;
        while (Board.inBounds(x, y)) {
            if (board.getPiece(x, y).side() == __side) break;
            if (board.getPiece(x, y).side() == Side.opponent(__side)) {
                vectorMoves.add(new Move(__x, __y, x, y));
                break;
            }
            vectorMoves.add(new Move(__x, __y, x, y)); //If board.getPiece(x, y) is EMPTY
            x += delX;
            y += delY;
        }
        return vectorMoves;
    }
    
    void pawnMark(boolean marked) {
        __pawnMarked = marked;
    }
    
    boolean pawnMarked() {
        return __pawnMarked;
    }
    
    double value() {
        return __value;
    };
    
    PieceType pieceType() {
        return __pieceType;
    }
    
    void setLocation(int x, int y) {
        __x = x;
        __y = y;
    }
    
    int getX() {
        return __x;
    }
    
    int getY() {
        return __y;
    }
    
    void setMoved() {
        __moved = true;
    }
    
    boolean moved() {
        return __moved;
    }
   
    abstract Piece copy();
    abstract BufferedImage getImage();
    abstract ArrayList<Move> legalMoves(Board board);
    
    Side __side;
    int __x;
    int __y;
    boolean __moved;
    double __value;
    PieceType __pieceType;
    boolean __pawnMarked;
    
    static final Piece EMPTY = new Empty();
}
    