package chess;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static chess.PieceType.*;
import static chess.MoveType.*;
import static chess.Side.*;



class King extends Piece {
    King(Side side, int x, int y, boolean moved) {
        super(side, x, y, moved);
        __value = 1000;
        __pieceType = KINGTYPE;
    }

    ArrayList<Move> legalMoves(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (!Board.inBounds(__x+i, __y+j)) continue;
                if (board.getPiece(__x+i, __y+j).side() != __side) {
                    legalMoves.add(new Move(__x, __y, __x+i, __y+j));
                }
            }
        }
        if (!moved()
            && !board.getPiece(0, __y).moved()
                && board.emptyBtwnHoriz(0, __x, __y)
                    && board.noThreatHoriz(0, __x, __y, __side)) {
            legalMoves.add(new Move(__x, __y, __x-2, __y));
        }
        if (!moved()
            && !board.getPiece(7, __y).moved()
                && board.emptyBtwnHoriz(7, __x, __y)
                    && board.noThreatHoriz(7, __x, __y, __side)) {
            legalMoves.add(new Move(__x, __y, __x+2, __y));
        }
        return legalMoves;
    }
    
    public String toString() {
        return __side.toString() + "K";
    }
    
    Piece copy() {
        return new King(__side, __x, __y, __moved);
    }
    
    BufferedImage getImage() {
        BufferedImage img = null;
        try {
            if (__side == WHITE) {
                img = ImageIO.read(new File("chess/whiteKing.png"));
            } else if (__side == BLACK) {
                img = ImageIO.read(new File("chess/blackKing.png"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return img;
    }
}    
    
    
    
    