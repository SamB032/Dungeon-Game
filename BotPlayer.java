import java.lang.Math;

/** Handles the bots logic and movement in the game */
public class BotPlayer extends Player{

    /** Constant array of directions in which the bot can move */
    private final char[] possibleMoves = {'N', 'S', 'W', 'E'};

    /** Method that it's the decision-making for the bots movement and position on the map
     * @param lookMap 5x5 look map grid
     * @param botCoordinates bots current coordinates
     * @param playerCoordinates player coordinates
     * @return bots new position */
    public int[] updateBotPosition(char[][] lookMap, int[] botCoordinates, int[] playerCoordinates){

        //Checks to see if player is in the bots 5x5 grid
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (lookMap[i][j] == 'P'){
                    //if player is in 5x5, call the moveTowardsPlayer method
                    return moveTowardsPlayer(lookMap, botCoordinates, playerCoordinates);
                }
            }
        }
        //else run the moveRandomly method
        return moveRandomly(lookMap, botCoordinates);
    }

    /** generates a random direction for the bot to move in, if it valid, return new coordinate, else try again
     * @param lookMap the 5x5 look map grid from the bots perspective
     * @param botCoordinates bots current coordinate position
     * @return bots new coordinate position */
    private int[] moveRandomly(char[][] lookMap, int[] botCoordinates){
        //If the player is not in the 5x5 grid we move in a random direction
        while (true){
            //Generates a random direction for the bot to move
            char direction = possibleMoves[ (int) (Math.random() * (possibleMoves.length - 1))];
            int rowMove = 0;
            int columnMove = 0;

            switch (direction) {
                case 'N' -> rowMove--;
                case 'S' -> rowMove++;
                case 'E' -> columnMove++;
                case 'W' -> columnMove--;
            }
            try{
                //Checks to see if the bot is moving to a valid space, if true return the new position, else generate a new direction to move
                if (lookMap[2 + rowMove][2 + columnMove] != '#'){
                    botCoordinates[0] += rowMove;
                    botCoordinates[1] += columnMove;
                    return botCoordinates;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                continue;
            }
        }
    }

    /** moves the bot according to the coordinates of the player
     * @param lookMap 5x5 look map grid
     * @param playerCoordinates player coordinates
     * @param botCoordinates bots coordinates
     * @return bots new position as it tries to attack the player */
    private int[] moveTowardsPlayer(char[][] lookMap, int[] botCoordinates, int[] playerCoordinates){
        int rowMove = 0;
        int columnMove = 0;

        if (botCoordinates[0] > playerCoordinates[0]){
            // if bot row > player row --> decrement the bot row
            rowMove--;
        } else if (botCoordinates[0] < playerCoordinates[0]){
            // if bot row < player row --> increment the bot row
            rowMove++;
        } else if (botCoordinates[1] > playerCoordinates[1]){
            // if bot column > player column --> decrement the bot column
            columnMove--;
        } else if (botCoordinates[1] < playerCoordinates[1]){
            // if bot column < player column --> increment the bot column
            columnMove++;
        }

        // need to check if new bot new coordinates are a contains a hash --> if true ignore the move
        if (lookMap[2 + rowMove][2 + columnMove] != '#'){
            // is valid move, save the coordinates and return the new bot position
            botCoordinates[0] += rowMove;
            botCoordinates[1] += columnMove;
        }
        return botCoordinates;
    }
}