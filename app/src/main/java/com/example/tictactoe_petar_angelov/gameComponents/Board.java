package com.example.tictactoe_petar_angelov.gameComponents;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private char[][] gameBoardMATRIX = new char[3][3];

    private int counter = 1;
    public boolean gameOverCHECK = false;

    public Board copyGameBoard(){

        Board copyBoardMATRIX = new Board();
        copyBoardMATRIX.gameOverCHECK = gameOverCHECK;
        copyBoardMATRIX.counter = this.counter;

        for (int i=0;i<3;i++){
            System.arraycopy(this.gameBoardMATRIX[i], 0, copyBoardMATRIX.gameBoardMATRIX[i], 0, 3);
        }

        return copyBoardMATRIX;
    }

    public void setOnPosition(int position){
        if (!gameOverCHECK){

            char score=' ';

            if(getCurrentPlayer().equals("You")){
                score = 'x';
            }else if(getCurrentPlayer().equals("AI")){
                score = 'o';
            }

            if(position == 1){
                gameBoardMATRIX[0][0] = score;
            }else if(position == 2){
                gameBoardMATRIX[0][1] = score;
            }else if(position == 3){
                gameBoardMATRIX[0][2] = score;
            }else if(position == 4){
                gameBoardMATRIX[1][0] = score;
            }else if(position == 5){
                gameBoardMATRIX[1][1] = score;
            }else if(position == 6){
                gameBoardMATRIX[1][2] = score;
            }else if(position == 7){
                gameBoardMATRIX[2][0] = score;
            }else if(position == 8){
                gameBoardMATRIX[2][1] = score;
            }else if(position ==9){
                gameBoardMATRIX[2][2] = score;
            }

            counter++;
            if (getGameResult()!=66666){
                gameOverCHECK =true;
            }
        }
    }


    public int getGameResult(){

        for (int i=0;i<3;i++){
            if (gameBoardMATRIX[i][0] == 'o' &&
                    gameBoardMATRIX[i][1] == 'o' &&
                    gameBoardMATRIX[i][2] == 'o'){
                return 2;
            }else if (gameBoardMATRIX[i][0] == 'x' &&
                    gameBoardMATRIX[i][1] == 'x' &&
                    gameBoardMATRIX[i][2] == 'x'){
                return 1;
            }
        }
        for (int i=0;i<3;i++){
            if (gameBoardMATRIX[0][i] == 'o' &&
                    gameBoardMATRIX[1][i] == 'o' &&
                    gameBoardMATRIX[2][i] == 'o'){
                return 2;
            }else if (gameBoardMATRIX[0][i] == 'x' &&
                    gameBoardMATRIX[1][i] == 'x' &&
                    gameBoardMATRIX[2][i] == 'x'){
                return 1;
            }
        }
        if (gameBoardMATRIX[0][0] == 'x' &&
                gameBoardMATRIX[1][1] == 'x' &&
                gameBoardMATRIX[2][2] == 'x' ||
                gameBoardMATRIX[2][0] == 'x' &&
                        gameBoardMATRIX[1][1] == 'x' &&
                        gameBoardMATRIX[0][2] == 'x'){
            return 1;
        }else if (gameBoardMATRIX[0][0] == 'o' &&
                gameBoardMATRIX[1][1] == 'o' &&
                gameBoardMATRIX[2][2] == 'o'||
                gameBoardMATRIX[2][0] == 'o' &&
                        gameBoardMATRIX[1][1] == 'o' &&
                        gameBoardMATRIX[0][2] == 'o'){
            return 2;
        }



        boolean checkIsThereZERO = false;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (gameBoardMATRIX[i][j]==0){
                    checkIsThereZERO=true;
                }
            }
        }
        if (!checkIsThereZERO){
            return 0;
        }
        return 66666;
    }


    public String getCurrentPlayer() {
        if (counter % 2 == 0)
            return  "AI";
        else
            return  "You";
    }



    public List availablePositions(){
        List<Integer> list =new ArrayList<>();
        int counterPosition=1;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (gameBoardMATRIX[i][j]==0){
                    list.add(counterPosition);
                }
                counterPosition++;
            }
        }
        return list;
    }
}
