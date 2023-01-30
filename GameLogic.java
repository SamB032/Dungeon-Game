import java.io.File;
import java.lang.Math;
import java.util.Arrays;

/** Contains the main logic for the game, as it processes it*/
public class GameLogic{
    
    private Map map;
    private HumanPlayer humanPlayer;
    private BotPlayer botPlayer;

    /** Constant variable that stores the look map grid size */
    private final int gridSize = 5;
    
    /** Stores the number of times the player has taken their go*/
    private int programCounter = 0;

    /** Default constructor that allows for a user to select a map */
	public GameLogic(){
        File directoryPath = new File("Example_maps");
        String[] contents = directoryPath.list();
        
        this.humanPlayer = new HumanPlayer();

        while (true){
            //Find all the names of the possible map names
            System.out.println("This is the list of possible maps: ");
            for (int i = 0; i < contents.length; i++){
                System.out.println("\t " + (i + 1) + ". " + contents[i]);
            }

            System.out.println("Please select map number you would like to play?");
            // Prompts for user input, checks to see it's a valid input and creates the map object passing the file name
            int choice = this.humanPlayer.getIntInput();
            if (choice >= 1 && choice <= contents.length){
                try{
                    this.map = new Map(contents[choice - 1]);
                }
                catch (Exception e){
                    //Crashes the program since the program cannot find the specified textile
                    System.err.println("\nError: \tThere was an error finding/reading the map from the specified text file,");
                    System.err.println("\tplease check you have the correct directory/textfil setup and re-run the program to try again\n");
                    e.printStackTrace();
                    System.exit(1);
                }
                break;
            } else {
                //User selects a number outside the number represented on the screen
                System.out.println("Invalid choice, please try again\n");
            }
        }
        System.out.print("\n"); //Appropriate spacing in the terminal
	}

    /** Spawns player/bot in and provides a constant looping prompting for user input until the player can quit*/
    private void runGame(){
        int[] playerCoordinates = spawnUser('B');
        int[] botCoordinates = spawnUser('P');
        
        this.botPlayer = new BotPlayer();
        //Uses the mutator of player class to store the humanPlayer and bot coordinates
        this.botPlayer.setCoordinates(botCoordinates);
        this.humanPlayer.setCoordinates(playerCoordinates);

        this.humanPlayer.displayGameInfo(this.map.getMapName(), this.map.getGoldRequired());

        while (true){
            String choice = humanPlayer.getStringInput().toUpperCase();

            //switch and case statements on the string input against a set commands from the specification
            switch (choice) {
                case "HELLO" -> System.out.println(displayGoldRequired());
                case "GOLD" -> System.out.println(displayPlayerGoldOwned());
                case "PICKUP" -> pickUpGold();
                case "COMMANDS" -> this.humanPlayer.printPossibleCommands();
                case "LOOK" -> this.humanPlayer.printLookMap(lookCommand(playerCoordinates));
                case "QUIT" -> leaveGame();
            }
            //Checks to see if a player has mentioned a 'move'
            if (choice.contains("MOVE")){
                try{
                    char direction = choice.replace("MOVE ", "").charAt(0); 
                    //Checks to see if a player has entered a single character for the direction
                    if (direction == 'N' || direction == 'S' || direction == 'W' || direction == 'E'){
                        updatePlayerPosition(direction);
                    } else {
                        System.out.println("Fail - Invalid Direction -> Check 'Commands' for possible directions");
                    }
                }
                catch (StringIndexOutOfBoundsException e){
                    System.out.println("Fail - Invalid Direction -> Check 'Commands' for possible directions");
                }
            }

            playerCoordinates = this.humanPlayer.getCoordinates();
            botCoordinates = this.botPlayer.getCoordinates(); // We have to define variable here else the look command doesnt work
            // Checks to see if the bot and the player occupy the same space (ie same coordinates)
            if (Arrays.equals(playerCoordinates, botCoordinates)){
                System.out.println("LOSE - the bot has got to the same position as you and killed you");
                System.exit(0);
            }

            programCounter += 1;
            // determines if it's the bots go, as bot has to look every turn, so it will move after 2 turns of the player
            if (programCounter % 2 == 0){
                botNextCommand();
            }
            System.out.print("\n");
        }
    }

    /** Generates a random valid coordinate the player or the bot can spawn at
     * @param opposingPlayer char, used to detect if bot spawns above a player
     * @return returns a unique spawn coordinates */
    private int[] spawnUser(char opposingPlayer){
        int[] mapDimensions = map.getMapDimensions();

        while (true){
            //Generates a random set of coordinates within the board
            int[] newCoordinates = {(int) (Math.random() * mapDimensions[0]), (int) (Math.random() * mapDimensions[1])};

            // Returns new coordinates if the char at that position is not (G or # or opposingPlayer), else generate new coordinates and repeat the process
            if ((map.getCharAtCoordinate(newCoordinates) != '#') && (map.getCharAtCoordinate(newCoordinates) != 'G') && (map.getCharAtCoordinate(newCoordinates) != opposingPlayer)){
                return newCoordinates;
            }
        }
    }

    /** Generates the 5x5 2d array around a coordinate
     * @param  coordinates coordinates in which the 5x5 grid will be around
     * @exception ArrayIndexOutOfBoundsException we try to access outside the border of the full map causes a instead, we can fill it with '#'
     * @return 5x5 grid */
    private char[][] lookCommand(int[] coordinates){
        char[][] fullMap = map.getMap();
        char[][] lookMap = new char[gridSize][gridSize];
        int[] playerCoordinates = this.humanPlayer.getCoordinates();
        int[] botCoordinates = this.botPlayer.getCoordinates();
        
        // Loops through the 5x5 grid and adds values around a center coordinate
        for (int i = 0; i < gridSize; i++){
            for (int j = 0; j < gridSize; j++){
                try{
					if (coordinates != botCoordinates && (coordinates[1] - 2 + j == botCoordinates[1] && coordinates[0] - 2 + i == botCoordinates[0])){
                        //places bot relative to the coordinates in the 5x5 grid
                        lookMap[i][j] = 'B';
                    }
                    else if ((coordinates[1] - 2 + j == playerCoordinates[1] && coordinates[0] - 2 + i == playerCoordinates[0]) || (i == 2 && j == 2 && coordinates == playerCoordinates)){
						//places player in center if player coordinates given or places player relative to 5x5 grid if bot coordinates are given
                        lookMap[i][j] = 'P';
					}
					else{
                        // Copies char other than 'B' and 'P' into 5x5 grid
						lookMap[i][j] = fullMap[coordinates[0] - 2 + i][coordinates[1] - 2 + j];
					}
                }
                catch (ArrayIndexOutOfBoundsException e){
                    lookMap[i][j] = '#';
                }
            }
        }
        return lookMap;
    }

    /** updates the player position on the board (also checks to see if it's a valid move)
     * @param direction char datatype that is either 'N' or 'S' or 'E' or 'W'
     * @exception ArrayIndexOutOfBoundsException when try to access outside map array, instead we treat this as a 'Fail'*/
    private void updatePlayerPosition(char direction){
        int[] playerCoordinates = this.humanPlayer.getCoordinates();
        int[] newCoordinates = {playerCoordinates[0], playerCoordinates[1]};

        switch (direction){
            case 'N' -> newCoordinates[0]--;
            case 'S' -> newCoordinates[0]++;
            case 'E' -> newCoordinates[1]++;
            case 'W' -> newCoordinates[1]--;
        }
        //Checks to see if the player is moving to a valid space (defined as anything but a #)
        try{
            if (map.getCharAtCoordinate(newCoordinates) != '#'){
                this.humanPlayer.setCoordinates(newCoordinates);
                System.out.println("Success");
            } else {
                System.out.println("Fail");
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Fail");
        }
    }

    /** Sets out what should be the bots next move
     * pick up if it occupies a space with gold
     * leave the game if it occupies an exit and has enough gold to win
     * else call the method that decides the bot movement (random or tracking player)
    */
    private void botNextCommand(){
        int[] botCoordinates = this.botPlayer.getCoordinates();
        if (this.map.getCharAtCoordinate(botCoordinates) == 'G'){
            //checks to see if a bot is above gold, and then loots it
            this.botPlayer.incrementGoldOwned();
            this.map.setCharAtCoordinate('.', botCoordinates);
        } else if (this.map.getCharAtCoordinate(botCoordinates) == 'E' && this.botPlayer.getGoldOwned() >= this.map.getGoldRequired()){
            // The bot has enough gold to win and is above exit, the player has lost as the bot has now won
            System.out.println("LOSE - the looter bot has got enough gold and left the map");
            System.exit(0);
        } else { //calls the method in bot that decides whether the bot will track player or moving randomly
            this.botPlayer.setCoordinates(this.botPlayer.updateBotPosition(lookCommand(botCoordinates), botCoordinates, this.humanPlayer.getCoordinates()));
        }
    }

    /** Returns the gold required to win.
     * @return "Gold to win: {gold required to win on the map}". */
    private String displayGoldRequired(){
        return "Gold to win: " + this.map.getGoldRequired();
    }
    
    /** Returns the gold collected so far
     * @return "Gold Owned: {gold owned by the human player}". */
    private String displayPlayerGoldOwned(){
        return "Gold owned: " + this.humanPlayer.getGoldOwned();
    }

    /** Checks to see if the player is above gold, if true increment gold, remove it from the map
    and print the message to the terminal*/
    private void pickUpGold(){
        if (this.map.getCharAtCoordinate(this.humanPlayer.getCoordinates()) == 'G'){
            this.humanPlayer.incrementGoldOwned();
            this.map.setCharAtCoordinate('.', this.humanPlayer.getCoordinates());
            System.out.println("Success\n" + displayPlayerGoldOwned());
        } else {
            System.out.println("Fail");
        }
    }

    /** Prints leaving message and ends the program if the player is on an Exit
    also prints to the terminal whether the player has won or lost*/
    private void leaveGame(){
        if (this.map.getCharAtCoordinate(this.humanPlayer.getCoordinates()) == 'E'){
            System.out.println("LEAVING GAME...");
            if (this.humanPlayer.getGoldOwned() >= this.map.getGoldRequired()){
                System.out.println("WIN - You have enough gold to leave the map");
            } else {
                System.out.println("LOSE - You did not have the required gold to win");
            }
        System.exit(0);
        }
    }

    /** Method that initiates the game by running default constructor to get a map then calls runGame method */
    public static void main(String[] args) {
		GameLogic logic = new GameLogic();
        logic.runGame();
    }
}