public class Position {

    private int x;
    private int y;
    private int index;

    public void translate(String loc, Board b){
        y=Character.getNumericValue(loc.charAt(0))-10;
        x=(int)loc.charAt(1)-49;
        index=y*3+x;
    }

    public int getIndex(){
        return index;
    }
    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }

}
