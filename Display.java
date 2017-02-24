import java.util.Scanner;

public class Display {

    private String s;
    private int level;
    private static String c=" XO";

    public String getWord(Board b){
        do{
            Scanner input = new Scanner(System.in);
            s=input.nextLine();
        }while(!validPos(s,b));
        return s;
    }

    // The level decides what the depth of the search-tree will be for the PC player
    // Level 3 is quite boring since you can play "draw" at best
    public int getLevel(){
        do{
            System.out.println("Which level do you want to play? 1=easy, 2=medium 3=unbeatable");
            Scanner input = new Scanner(System.in);
            s=input.nextLine();
        }while(!validLevel());
        return level*3;
    }

    private boolean validLevel(){
        if(s.length()!=1){
            return errorMessage("Please type in only 1 number");
        }
        if(!Character.isDigit(s.charAt(0))){
            return errorMessage("Please type in a number between 1 and 3");
        }
        level=Character.getNumericValue(s.charAt(0));
        if(!(level>=1 && level<=3)){
            return errorMessage("Please type in a number between 1 and 3");
        }
        return true;
    }

    // The order of this method is not very beautiful, excusez moi!
    private boolean validPos(String s, Board b){
        if(s.length()!=2){
            return errorMessage("You need to type a character and an number");
        }
        Position p = new Position();
        p.translate(s,b);
        int x=p.getX();
        int y=p.getY();
        if(x < 0 || x > 2 || y < 0 || y > 2){
            return errorMessage("The chars are out of range, try again");
        }
        int index=p.getIndex();
        if(b.set(index)){
            return errorMessage("Board is already filled here");
        }
        return true;
    }

    private boolean errorMessage(String error){
        System.out.println(error);
        return false;
    }

    public void printTurn(Board b){
        System.out.printf("It's %c's turn!\n",c.charAt(b.getTurn()));
    }

    public void printBoard(Board b){
        int board[][]=b.getBoard();
        System.out.printf("  | 1 | 2 | 3 | ");
        for(int i=0;i<3;i++){
            System.out.printf("\n--+---+---+---+\n%c |",97+i);
            for(int j=0;j<3;j++){
                System.out.printf(" %c |",c.charAt(board[i][j]));
            }
        }
        System.out.printf("\n--+---+---+---+\n\n");
    }

    public void printResult(Board b, int loc){
        int winner=b.findWinner(loc);
        if(winner==0){
            System.out.println("Game finished. Draw game!");
        }else{
            System.out.printf("Game finished. User %c wins the game!\n",c.charAt(winner));
        }
    }
}
