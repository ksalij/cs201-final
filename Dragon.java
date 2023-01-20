import java.lang.Math;

/**
* Dragon class, subclass of Mob. Special move is fire breath. 
*/
public class Dragon extends Mob {
  
  /**
  * Contructor for Dragon, calls the superclass Mob Constructor to set instance variables. Default health and is 40 and default attackPower is 4.
  */
  public Dragon() {
    super(40, 4, "sleep", new String[]{"attack", "fire breath"});
  }
  
  /**
  * Gets the name of the Dragon Mob. 
  * @return    returns the String "Dragon"
  */
  public String getName() {
    return "Dragon";
  }

  /**
  * Overrides randomMove() in the Mob class but calls randomMove() from the Mob class and adds the Dragon's special moves to the choices avaliable to be randomly choosen.
  * @param mob    Mob being attacked
  * @return    returns a String of the attack used
  */
  public String randomMove(Mob mob) {
    String move = super.randomMove(mob);
    if(move.equals("fire breath")) {
      fireBreath(mob);
    }
    return move;
  }

  /**
  * Base damage is 3, but chance for Mob to be on fire.
  * @param mob    Mob being attacked
  */
  public void fireBreath(Mob mob) {
    mob.setHealth(mob.getHealth()-3);
    if(getStatus().equals("dead")) return;

    int chance = (int) (Math.random() * 100);
    if(chance < 20) {
      mob.setStatus("fire");
      if(mob.getName().equals("You!")) {
        System.out.println("Ahhhh!! You're on fire!");
      }
    }
  }

}