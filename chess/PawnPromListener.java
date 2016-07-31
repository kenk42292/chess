package chess;

import javax.swing.JFrame;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;

import static chess.PawnPromChoice.*;

class PawnPromListener implements ActionListener {
    PawnPromListener(JFrame frame, PawnPromChoice piece, PawnPromChoice[] choicePiece) {
        __frame = frame;
        __piece = piece;
        __choicePiece = choicePiece;
    }

    public void actionPerformed(ActionEvent e) {
        __choicePiece[0] = __piece;
        __frame.dispose();
    }
    JFrame __frame;
    PawnPromChoice __piece;
    PawnPromChoice[] __choicePiece;
}

