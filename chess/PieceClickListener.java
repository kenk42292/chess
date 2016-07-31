package chess;

import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;

import static chess.Side.*;

class PieceClickListener implements ActionListener {
    public PieceClickListener(int x, int y, int[] moveCommand, Side side) {
        __x = x;
        __y = y;
        __moveCommand = moveCommand;
        __side = side;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (__side == WHITE) {
            if (__moveCommand[0] == -1) {
                __moveCommand[0] = __x;
                __moveCommand[1] = __y;
            } else {
                __moveCommand[2] = __x;
                __moveCommand[3] = __y;
            }
        } else if (__side == BLACK) {
            if (__moveCommand[0] == -1) {
                __moveCommand[0] = 7-__x;
                __moveCommand[1] = 7-__y;
            } else {
                __moveCommand[2] = 7-__x;
                __moveCommand[3] = 7-__y;
            }
        
        }
    }
    
    int __x;
    int __y;
    int[] __moveCommand;
    Side __side;
}