package main;

import java.util.Stack;

/*
 棋盘数据结构
 */
public class Core {
    //棋盘大小
    private int[][] core;
    private int x;
    private int y;
    //记录一个棋子的类
    class Chess{
        int x;
        int y;
        public Chess(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
    //栈，用于悔棋
    Stack<Chess> stack;
    //构造方法
    public Core(int x,int y){
        stack = new Stack<>();
        core=new int[x][y];
        this.x=x;
        this.y=y;
    }
    //判断输赢
    public int checkWin(int x,int y,int var){
        //横向判断
        int trans=0;
        for(int i=x-4;i<x-5;i++){
            if(i<0||i>=this.x)
                continue;
            if(core[i][y]==var){
                trans++;
            }
            else{
                trans=0;
            }
            if(trans==5)return var;
        }
        //纵向判断
        int vertical=0;
        for(int i=y-4;i<y+5;i++){
            if(i<0||i>=this.y)
                continue;
            if(core[x][i]==var){
                vertical++;
            }
            else{
                vertical=0;
            }
            if(vertical==5)return var;
        }
        //从左上到右下判断
        int leftUPToDown = 0;
        for(int i=x-4,j=y+4;i<x+5&&j>y-5;i++,j--) {
            if(i<0||i>=this.x||j<0||j>=this.y) continue;
            if(core[i][j]==var) {
                leftUPToDown++;
            }else {
                leftUPToDown=0;
            }
            if(leftUPToDown==5) return var;
        }
        //从左下到右上判断
        int leftDownToUP = 0;
        for(int i=x+4,j=y+4;i>x-5&&j>y-5;i--,j--) {
            if(i<0||i>=this.x||j<0||j>=this.y) continue;
            if(core[i][j]==var) {
                leftDownToUP++;
            }else {
                leftDownToUP=0;
            }
            if(leftDownToUP==5) return var;
        }
        return 0;
    }
    /**
     * 在该位置下棋  1:white 2:black
     * @param x 横坐标
     * @param y 纵坐标
     * @param var 棋子种类
     * @return 1:white赢   2:black赢
     */
    public int ChessIt(int x,int y,int var) {
        if(canChess(x,y)) {
            core[x][y] =var;
            Chess chess = new Chess(x,y);
            stack.push(chess);
            return checkWin(x, y, var);
        }
        else return -1;
    }
    public boolean canChess(int x,int y){
        if(x>=0&&x<this.x&&y>0&&y<this.y){
            return true;
        }else{
            return false;
        }
    }
    //悔棋
    public boolean RetChess() {
        if(stack.isEmpty()) return false;
        Chess chess = stack.pop();
        core[chess.x][chess.y]= 0;
        return true;
    }
    //获得棋盘状态
    public int[][] getCore(){
        return this.core;
    }
    //重新开始
    public void Restart() {
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                this.core[i][j]=0;
            }
        }
        this.stack.clear();
    }

}
