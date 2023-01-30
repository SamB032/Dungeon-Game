import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

/** Reads and contains in memory the map of the game. */
public class Map{
	/** The folder in which maps are stored within */
	private final String mapDirectory = "Example_maps/";

	/** 2d array that represents the map*/
	private char[][] map;

	/** String variable of the name of the map */
	private String mapName;

	/** Gold required for the human player to win */
	private int goldRequired;

	/** number of rows in the map */
	private int numberOfRows;
	
	/** number of columns in the map */
	private int numberOfColumns;
	
	/** Default Constructor that accepts a map to read in from.
	 * @param fileName name of the file that the player has selected 
	 * @throws Exception passes the exception if one is created onto gameLogic (generated if an issue with reading the file) */
	public Map(String fileName) throws Exception{
		readMap(mapDirectory + fileName);
	}
	
    /** Reads the map from file, storing in a 2d array.
     * @param filePath Name of the map's file as a directory path
	 * @throws FileNotFoundException file cannot be found
	 * @throws IOException the file cannot be accessed */
    private void readMap(String filePath) throws FileNotFoundException, IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		this.mapName = reader.readLine().replace("name ", ""); //Gets map name from file
		this.goldRequired = Integer.parseInt(reader.readLine().replace("win ", "")); //Gets gold required to win from text file
		this.numberOfRows = getNumberOfLines(filePath) - 2;
		
		this.map = new char[this.numberOfRows][];

		// Loops through each column and row in file and adds it to the map 2d array
		for (int row = 0;  row < this.numberOfRows; row++) {
			map[row] = reader.readLine().toCharArray();
		}
		this.numberOfColumns = map[0].length;
    }

	/** finds the number of lines in the text file
	 * @param filePath the filePath of the file we want to find the number of lines for
	 * @throws FileNotFoundException the filePath cannot be found
	 * @return the total number of lines in the text file */
	private int getNumberOfLines(String filePath) throws FileNotFoundException{
		Scanner scanner = new Scanner(new FileReader(filePath));
		int count = 0;
		while (scanner.hasNextLine()) {
			count++;
			scanner.nextLine();
		}
		return count;
	}

	/** Accessor that returns gold required to win, used in when the player types "HELLO"
	 * @return the gold that is required to win */
	public int getGoldRequired(){
		return this.goldRequired;
	}

	/** Accessor that returns name of the map
	 * @return name of map */
	public String getMapName(){
		return this.mapName;
	}

	/** Accessor that method returns the map instance variable
	 * @return 2d array of the map */
	public char[][] getMap(){
		return this.map;
	}

	/** find what char is at a specific coordinate 
	 * @param coordinate the coordinate we want to get the char at
	 * @return char at that coordinate */
	public char getCharAtCoordinate(int[] coordinate){
		return this.map[coordinate[0]][coordinate[1]];
	}

    /** Get dimensions of the file 
     * @return int array {number of rows, number of columns}*/
	public int[] getMapDimensions(){
		return new int[] {this.numberOfRows, this.numberOfColumns};
	}
	
	/** sets a char at a specific coordinate 
	 * @param item the item we would like to set it as
	 * @param coordinate the coordinate we would like to set the char at */
	public void setCharAtCoordinate(char item, int[] coordinate){
		this.map[coordinate[0]][coordinate[1]] = item;
	}

}