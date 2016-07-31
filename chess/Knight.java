package chess;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static chess.PieceType.*;
import static chess.MoveType.*;
import static chess.Side.*;

class Knight extends Piece {
    Knight(Side side, int x, int y, boolean moved) {
        super(side, x, y, moved);
        __value = 3;
        __pieceType = KNIGHTTYPE;
    }
    
    ArrayList<Move> legalMoves(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        int[] coord1 = {-1, 1};
        int[] coord2 = {-2, 2};
        for (int x : coord1) {
            for (int y : coord2) {
                if (Board.inBounds(__x+x, __y+y) && board.getPiece(__x+x, __y+y).side() != __side) {
                    legalMoves.add(new Move(__x, __y, __x+x, __y+y));
                }
            }
        }
        for (int x : coord2) {
            for (int y : coord1) {
                if (Board.inBounds(__x+x, __y+y) && board.getPiece(__x+x, __y+y).side() != __side) {
                    legalMoves.add(new Move(__x, __y, __x+x, __y+y));
                }
            }
        }
        return legalMoves;
    }
    
    public String toString() {
        return __side.toString() + "N";
    }
    
    Piece copy() {
        return new Knight(__side, __x, __y, __moved);
    }
    
    BufferedImage getImage() {
        BufferedImage img = null;
        try {
            if (__side == WHITE) {
                img = ImageIO.read(new File("chess/whiteKnight.png"));
            } else if (__side == BLACK) {
                img = ImageIO.read(new File("chess/blackKnight.png"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return img;
    }
}    
    
    