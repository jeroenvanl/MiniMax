// I used the "MiniMax" algorithm with adaptive search depth, according to
// to level of the game and the amount of positions filled. The methods are
// inspired on the pseudo-code on https://en.wikipedia.org/wiki/Minimax

public class Ai {

    private int pos;
    private int totalScore;

    // checks best location for pc to put the 'O'
    public String getLoc(Board b, int level){
        int searchDepth=java.lang.Math.max(level-b.getFill(),1);
        totalScore=-1000;
        for(int i=0;i<9;i++){
            if(!b.set(i)){ // check whether it has been set or not
                int val=miniMax(b,searchDepth,i);
                updateScore(val,i);
                b.fillLoc(i,0);
            }
        }
        return convertToString();
    }

    //updates score when suggested position increases chances of winning
    private void updateScore(int val, int i){
        if(val>totalScore){
            totalScore=val;
            pos=i;
        }
    }

    // This function checks the consequences of new position on the board of
    // the PC or the player.
    private int miniMax(Board b, int depth, int loc){
        b.fillLoc(loc, b.getTurn());
        if(depth==0 || b.getFill()==9 || b.findWinner(loc)!=0){ // base case
            return evaluate(b,depth,b.getTurn());
        }else{
            int maxScore = 3000 -(b.getTurn()*2000);
            b.oscillateTurns();
            for (int i = 0; i<9; i++) {
                if (!b.set(i)) { // check whether it has been set or not
                    int value=miniMax(b,depth-1,i);
                    maxScore=updateMaxScore(b.getTurn(),value,maxScore);
                    b.fillLoc(i, 0); // back track
                }
            }
            b.oscillateTurns();
            return maxScore;
        }
    }

    private int updateMaxScore(int turn, int value, int maxScore){
        if(turn==2 && value<maxScore){
            return value;
        }else if(turn==1 && value>maxScore){
            return value;
        }
        return maxScore;
    }

    // Heuristics to determine how good a certain position is for player
    // The values in the evaluation function are inspired on the code here:
    // http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html
    private int evaluate (Board b, int depth, int turn){
        int totalscore=0;
        totalscore+=evaluateRow(b,turn);
        totalscore+=evaluateCol(b,turn);
        totalscore+=evaluateDiags(b,turn);
        if(turn==1){
            return (totalscore*(depth+1)); //give priority to less digging
        }else{
            return (-totalscore);
        }
    }

    private int evaluateRow(Board b, int turn){
        int score=0;
        for (int i = 0; i < 3; i++){
            int row=0;
            for (int j = 0; j < 3; j++) {
                if(b.getState(i*3+j)==turn){
                    row++;
                }
            }
            if(row>1){
                score+=Math.pow(10,(row-1));
            }
        }
        return score;
    }

    private int evaluateCol(Board b, int turn){
        int score=0;
        for (int i = 0; i < 3; i++) {
            int col=0;
            for (int j = 0; j < 3; j++) {
                if(b.getState(i+j*3)==turn){
                    col++;
                }
            }
            if(col>1){
                score+=Math.pow(10,(col-1));
            }
        }
        return score;
    }

    private int evaluateDiags(Board b, int turn){
        int diag=0;
        int crossdiag=0;
        int score=0;
        for(int i=0;i<12;i+=4){
            if(b.getState(i)==turn){
                diag++;
            }
        }
        if(diag>1){
            score+=Math.pow(10,(diag-1));
        }
        for(int i=2;i<8;i+=2){
            if(b.getState(i)==turn){
                crossdiag++;
            }
        }
        if(crossdiag>1){
            score+=Math.pow(10,(crossdiag-1));
        }
        return score;
    }

    private String convertToString(){
        char a=(char)(pos/3+97);
        char b=(char)(pos%3+49);
        return new StringBuilder().append(a).append(b).toString();
    }
}