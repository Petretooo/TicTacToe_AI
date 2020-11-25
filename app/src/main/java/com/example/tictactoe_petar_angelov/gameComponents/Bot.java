package com.example.tictactoe_petar_angelov.gameComponents;

import com.example.tictactoe_petar_angelov.PlayActivity;

import java.util.List;

public class Bot {

    public int position = 0;
    private int score;

    public Bot() {

    }

    private Bot(int score){
        this.score = score;
    }

    private Bot(int score, int position){
        this.score = score;
        this.position=position;
    }


    private Bot score(Board game){
        int score ;
        switch (game.getGameResult()){
            case 0:score=0; break;
            case 1:score=1; break;
            case 2:score=-1; break;
            default: score =999; break;
        }
        return new Bot(score);
    }

    public Bot findingPerfectPosition(Board game){

        game.getGameResult();

        if (game.gameOverCHECK){
            PlayActivity.counter++;
            return score(game);
        }else {

            int currentScore = 0;
            int bestScoreForMove = 0;
            int bestMove=0;

            Board gameCopy;
            if (game.getCurrentPlayer().equals("You")){
                bestScoreForMove=-666;
            }else if(game.getCurrentPlayer().equals("AI")){
                bestScoreForMove=666;
            }


            List<Integer> moves = game.availablePositions();
            for (int i=0;i<moves.size();i++){

                gameCopy=game.copyGameBoard();
                gameCopy.setOnPosition(moves.get(i));

                currentScore= findingPerfectPosition(gameCopy).score;

                if (game.getCurrentPlayer().equals("You")){
                    if (currentScore > bestScoreForMove){
                        bestScoreForMove=currentScore;
                        bestMove=moves.get(i);
                    }
                }else {
                    if (currentScore < bestScoreForMove){
                        bestScoreForMove=currentScore;
                        bestMove=moves.get(i);
                    }
                }
            }
            return new Bot(bestScoreForMove,bestMove);
        }
    }
}
