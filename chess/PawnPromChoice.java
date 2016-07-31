package chess;

enum PawnPromChoice {
    CHOICEROOK, CHOICEKNIGHT, CHOICEBISHOP, CHOICEQUEEN;

    static PawnPromChoice parse(String playerChoice) {
        PawnPromChoice choice = CHOICEQUEEN;
        System.out.println("playerChoice: " + playerChoice);
        switch (playerChoice) {
            case "R":
                choice = CHOICEROOK;
                break;
            case "N":
                choice = CHOICEKNIGHT;
                break;
            case "B":
                choice = CHOICEBISHOP;
                break;
            case "Q":
                choice = CHOICEQUEEN;
                break;
            default:
                choice = CHOICEQUEEN;
        }
        return choice;
    }
}

