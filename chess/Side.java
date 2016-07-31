package chess;

enum Side {
    BLACK("B"), WHITE("W");
    
    Side(String text) {
        __text = text;
    }
    
    static Side opponent(Side side) {
        if (side == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
    
    public String toString() {
        return __text;
    }
    
    String __text;
}