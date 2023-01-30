import java.util.Scanner;

/** Runs the game with a human player and contains code needed to read inputs.*/
public class HumanPlayer extends Player{
    /** Makes the scanner object a class variable */
    private Scanner scanner;

    /** Constant String array of possible inputs from the user */
    private final String[] possibleCommands = {"HELLO", "GOLD", "PICKUP", "MOVE {N, E, S or W}", "LOOK"};

    /** default constructor that initialises the scanner object and manages the constructor of the parent class */
    public HumanPlayer(){
        this.scanner = new Scanner(System.in);
    }

    /** prompts the user for a string input
     * @return int string from the user */
    public String getStringInput(){
        return this.scanner.nextLine().trim();
    }

    /** prompts the user for an integer input
     * @return int input from the user */
    public int getIntInput(){
        return this.scanner.nextInt();
    }

    /** Prints the map name and gold required to the terminal
     * @param mapName name of the map
     * @param goldRequired gold required for that map */
    public void displayGameInfo(String mapName, int goldRequired){
        System.out.println("Map name: '" + mapName + "'\nGold required: "
        + goldRequired + "\nTo get the list of all possible commands, type 'commands'");
    }

    /** Prints the 5x5 look map grid to the terminal
     * @param lookMap 5x5 grid look map */
    public void printLookMap(char[][] lookMap){
        // Prints the look map to the terminal (in grid format)
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                System.out.print(lookMap[i][j]);
            }
            System.out.print("\n");
        }
    }
    
    /** Prints each command in the possibleCommands Array */
    public void printPossibleCommands(){
        System.out.println("All possible commands:");
        for (String command : possibleCommands) {
            System.out.println("\t- " + command);
        }
    }
}