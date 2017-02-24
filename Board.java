public class Board {

    private int state[][];
    private int turn;
    private int filled;

    // Only these fields need to be initialized in constructor
    Board(){
        state=new int[3][3];
        filled=0;
    }

    public int[][] getBoard(){
        return state;
    }

    public int getTurn(){
        return turn;
    }

    public int getFill(){
        return filled;
    }

    public int getState(int i) {
        return state[i/3][i%3];
    }

    // This func not in construct because has to be called after test func
    public void setRandomTurn(){
        turn=(Math.random()<0.5)?1:2;
    }

    public void oscillateTurns() {
        turn=3-turn;
    }

    public void fillLoc(int index, int num){
        state[index/3][index%3]=num;
        if(num==0){
            filled--;
        }else{
            filled++;
        }
    }

    public boolean set(int i){
        return state[i/3][i%3]!=0;
    }

    // Returns 0 for no winner, 1 for player 1, 2 for player 2
    public int findWinner(int loc){
        int x=loc%3;
        int y=loc/3;
        if(turn==state[y][(x+1)%3] &&
                turn==state[y][(x+2)%3]){
            return turn;
        }
        else if(turn==state[(y+1)%3][x] &&
                turn==state[(y+2)%3][x]){
            return turn;
        }
        else if(state[0][0]==state[1][1] &&
                state[1][1]==state[2][2] &&
                turn==state[0][0]){
            return turn;
        }
        else if(state[0][2]==state[1][1] &&
                state[1][1]==state[2][0] &&
                turn==state[0][2]){
            return turn;
        }else{
            return 0;
        }
    }


    public void tests(){
        //row checks
        testFindWinner("111220000",1,5,0,true);
        testFindWinner("102202111",1,7,8,true);
        testFindWinner("222110000",2,5,1,true);
        testFindWinner("000222110",2,5,5,true);
        //col checks
        testFindWinner("122100100",1,5,6,true);
        testFindWinner("022121021",2,7,4,true);
        testFindWinner("120120020",2,5,7,true);
        //diag checks
        testFindWinner("122010001",1,5,8,true);
        testFindWinner("102020201",2,5,6,true);
        //draw checks
        testFindWinner("122211212",2,9,1,false);
        testFindWinner("112122121",1,9,1,false);
        //false checks
        testFindWinner("000002000",2,1,5,false);
        testFindWinner("000000221",2,3,7,false);
        testFindWinner("121212000",2,6,1,false);
        testFindWinner("121122010",1,7,3,false);
        testFindWinner("211102121",2,8,0,false);
        testFindWinner("000000000",1,0,6,false);
    }

    private void testFindWinner(String board_, int turn_, int filled_, int loc, boolean b){
        filled=filled_;
        turn=turn_;
        for(int i=0;i<9;i++){
            state[i/3][i%3]=(int)board_.charAt(i)-48;
        }
        assert b==(findWinner(loc)!=0);
        assert b==(findWinner(loc)==turn);
    }
}

