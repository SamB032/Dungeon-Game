/** Inherited by humanPlayer and botPlayer, stores there coordinates and gold owned*/
public class Player{
    
    /** coordinates of the player */
    private int[] coordinates;

    /** Number of gold the player owns, starts at o*/
    private int goldOwned = 0;

    /* No default constructor for this class as we initialise humanPlayer before knowing the coordinates
    ,so when we want to set the coordinates, we just use the mutator */

    /** Mutator that increments the gold owned */
    public void incrementGoldOwned(){
        this.goldOwned++;
    }

    /** Mutator that updates the coordinates class variable to a new value
     @param coordinates coordinates to update with*/
    public void setCoordinates(int[] coordinates){
        this.coordinates = coordinates;
    }

    /** Accessor that returns gold owned as an integer (this starts at 0 and increases as the player picks up gold)
     * @return goldOwned by the player/bot */ 
    public int getGoldOwned(){
        return this.goldOwned;
    }

    /** Accessor that returns player/bot coordinates (in the format {row number, column number})
     * @return player/bot coordinates */
    public int[] getCoordinates(){
        return this.coordinates;
    }
}