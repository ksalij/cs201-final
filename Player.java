
/**
* Player class, subclass of Mob. Special move is heal. 
*/
public class Player extends Mob {
  
  /**
  * Contructor for Player, calls the superclass Mob Constructor to set instance variables. Default health is 40 and default attackPower is 5.
  */
  public Player() {
    super(40, 5, "none", new String[]{"attack", "heal"});
  }

  /**
  * Gets the name of the Player 
  * @return    returns the String "You!"
  */
  public String getName() {
    return "You!";
  }

  /**
  * Heals the Player with health points equal to their attackPower.
  */
  public void heal() {
    setHealth(getHealth()+ getAP());
  }

}