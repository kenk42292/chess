package chess;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static chess.PieceType.*;
import static chess.Side.*;

class Queen extends Piece {
    Queen(Side side, int x, int y, boolean moved) {
        super(side, x, y, moved);
        __value = 9;
        __pieceType = QUEENTYPE;
    }

    ArrayList<Move> legalMoves(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        legalMoves.addAll(vectorMoves(-1, 0, board));
        legalMoves.addAll(vectorMoves(1, 0, board));
        legalMoves.addAll(vectorMoves(0, -1, board));
        legalMoves.addAll(vectorMoves(0, 1, board));
        legalMoves.addAll(vectorMoves(-1, -1, board));
        legalMoves.addAll(vectorMoves(-1, 1, board));
        legalMoves.addAll(vectorMoves(1, -1, board));
        legalMoves.addAll(vectorMoves(1, 1, board));
        return legalMoves;
    }
    
    public String toString() {
        return __side.toString() + "Q";
    }
    
    Piece copy() {
        return new Queen(__side, __x, __y, __moved);
    }
    
    BufferedImage getImage() {
        BufferedImage img = null;
        try {
            if (__side == WHITE) {
                img = ImageIO.read(new File("chess/whiteQueen.png"));
            } else if (__side == BLACK) {
                img = ImageIO.read(new File("chess/blackQueen.png"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return img;
    }
}    
