package chess;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import static chess.PieceType.*;


class Empty extends Piece {
    Empty() {
        super(null, 0, 0, false);
        __value = 0;
        __pieceType = EMPTYTYPE;
    }
    
    ArrayList<Move> legalMoves(Board board) {
        return LEGAL_MOVES;
    }
    
    double value() {
        return __value;
    }
    
    public String toString() {
        return "  ";
    }
    
    Piece copy() {
        return new Empty();
    }
    
    Side __side = null;
    
    static final ArrayList<Move> LEGAL_MOVES = new ArrayList<Move>();
    
    BufferedImage getImage() {
        BufferedImage img = null;
        return img;
    }
}
