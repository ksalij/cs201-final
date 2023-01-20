import java.lang.Math;

/**
* Mob class, has basic attack move and handles all the statusEffects possible
*/
public class Mob {
  private int health;
  private final int maxHealth;
  private int attackPower;
  private String status;
  private String[] moves;
  private boolean canMove = true;
  
  /**
  * Contructor for generic Mob class. Default health is 20 and default attackPower is 3. 
  */
  public Mob() {
    health = 20;
    maxHealth = 20;
    attackPower = 3;
    status = "none";
    moves = new String[]{"attack"};
  }

  /**
  * Constructor for Mob if to set different values for health, attackPower, status, and moves. Called by subclasses to set their instance variables. 
  */
  public Mob(int startHealth, int ap, String startStatus, String[] movesArray) {
    health = startHealth;
    maxHealth = startHealth;
    attackPower = ap;
    status = startStatus;
    moves = movesArray;
  }

  /**
  * Returns a String of the important information about the Mob: their name, current health, and status. 
  * @return    a String of the Mob's name, health, and status
  */
  public String toString() {
    String s = "Name: " + getName() + "\nHealth: " + health + "\nStatus: " + status;
    return s;
  }
  
  /**
  * Generic attack, reduces the health of the Mob being attacked
  * @param mob    Mob being attacked
  */
  public void attack(Mob mob) {
    int chance = (int) (Math.random() * 100);
    if(chance < 10) {
      int critPower = (attackPower*2);
      if (critPower == attackPower) {
        critPower = attackPower+1;
      }
      //10% chance of critical hit
      mob.setHealth(mob.getHealth()-(critPower));
      System.out.println("Critical Hit!");
    }
    else mob.setHealth(mob.getHealth()-attackPower);
  }

  /**
  * Chooses a random move from the list of moves avaliable to the Mob.
  * @return    returns a String of the move choosen
  */
  public String randomMove(Mob mob) {
    int choice = (int) (Math.random() * (moves.length));
    if(choice == 0) {
      attack(mob);
    }
    return moves[choice];
  }

  /**
  * Gets the name of a generic Mob
  * @return    returns the String "Mob"
  */
  public String getName() {
    return "Mob";
  }

  /**
  * Gets the health of this Mob.
  * @return returns an int of the health of the Mob
  */
  public int getHealth() {
    return health;
  }

  /**
  * Gets the status of this Mob.
  * @return returns a String of the status of the Mob
  */
  public String getStatus() {
    return status;
  }

  /**
  * Gets the attackPower of this Mob.
  * @return returns an int of the attackPower of the Mob
  */
  public int getAP() {
    return attackPower;
  }

  /**
  * Gets the avaliable moves of this Mob.
  * @return returns a String array of the moves of the Mob
  */
  public String[] getMoves() {
    return moves;
  }

  /**
  * Checks whether this Mob can move.
  * @return    returns a boolean - true if this mob can move, false otherwise
  */
  public boolean canMove() {
    return canMove;
  }

  /**
  * Sets a new boolean to the canMove variable. 
  * @param newCanMove    the new boolean value to be set. True, if the Mob can move, false if they cannot. 
  */
  public void setCanMove(boolean newCanMove) {
    canMove = newCanMove;
  }

  /**
  * Sets the health of this Mob. Health cannot go below 0 or above their max health.
  * @param newHealth    int of the new health value
  */
  public void setHealth(int newHealth) {
    if(newHealth <= 0) {
      health = 0;
      setStatus("dead");
    }
    else if(newHealth > maxHealth) {
      health = maxHealth;
      if(this.getName().equals("You!")) {
        System.out.println("You cannot increase you health beyond your maximum health.");
      }
    }
    else health = newHealth;
  }

  /**
  * Sets the status of this Mob. Mobs can only have one status type active at a time. 
  * @param newStatus    String of the new status
  */
  public void setStatus(String newStatus) {
    status = newStatus;
  }

  /**
  * Checks for status effects and calls the method needed if there is a status that has effects.
  */
  public void statusEffects() {
    if(status.equals("poison")) {
      poisoned();
    }
    if(status.equals("sleep")) {
      canMove = false;
      asleep();
    }
    if(status.equals("fire")) {
      burning();
    }
  }

  /**
  * Called when the Mob's status is poison. Health is reduced by 1 and there is a chance to become not poisoned. 
  */
  public void poisoned() {
    setHealth(getHealth()-1);
    if(getStatus().equals("dead")) return;
    int chance = (int) (Math.random() * 100);
    if(chance < 40) {
      setStatus("none");
      if(this.getName().equals("You!")) {
        System.out.println("Whooo! The poison has worn off!");
      }
    }
  }

  /**
  * Called when the Mob's status is sleep. There is a chance to wake up. 
  */
  public void asleep() {
    int chance = (int) (Math.random() * 100);
    if(chance < 50) {
      setStatus("none");
      canMove = true;
      if(this.getName().equals("You!")) {
        System.out.println("Hurrah! You've awoken!");
      }
      if(this.getName().equals("Dragon")) {
        System.out.println("The Dragon has risen from its sleep!");
      }
    }
  }

  /**
  * Called when the Mob's status is fire. Health is reduced by 2 and there is a chance to be not on fire. 
  */
  public void burning() {
    setHealth(getHealth()-2);
    if(getStatus().equals("dead")) return;
    int chance = (int) (Math.random() * 100);
    if(chance < 50) {
      setStatus("none");
      if(this.getName().equals("You!")) {
        System.out.println("Phew! You're not on fire anymore.");
      }
    }
  }
}