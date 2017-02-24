// Hello Ian! Some comments on my program:
// 1. I made methods as private as possible
// 2. Testing is done on the findWinner method in the Board class
// 3. Ai extension with three levels Easy, Medium and Unbeatable

public class Oxo {

    private int level;

    public static void main(String args[]){
        Oxo oxo=new Oxo();
        Board b=new Board();
        Display d=new Display();
        Position p=new Position();
        Ai ai=new Ai();
        b.tests();
        oxo.playGame(b,d,p,ai);
    }

    private void playGame(Board b, Display d, Position p, Ai ai){
        b.setRandomTurn();
        level=d.getLevel();
        do{
            b.oscillateTurns();
            d.printTurn(b);
            translateInput(b,ai,p,d);
            b.fillLoc(p.getIndex(),b.getTurn());
            d.printBoard(b);
        }while(!gameEnded(p,b));
        d.printResult(b,p.getIndex());
    }

    // Translates the user-input or PC position to a location on the board
    private void translateInput(Board b, Ai ai, Position p, Display d){
        if(b.getTurn()==1){
            p.translate(ai.getLoc(b,level),b);
        }else{
            p.translate(d.getWord(b),b);
        }
    }

    private boolean gameEnded(Position p, Board b){
        if(b.getFill()==9){
            return true;
        }else if(b.findWinner(p.getIndex())!=0){
            return true;
        }
        return false;
    }
}
