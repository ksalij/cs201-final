import java.lang.Math;

/**
* Wizard class, subclass of Mob. Special move is sleep. 
*/
public class Wizard extends Mob {
  
  /**
  * Contructor for Wizard, calls the superclass Mob Constructor to set instance variables. Default health and is 30 and default attackPower is 3.
  */
  public Wizard() {
    super(30, 3, "none", new String[]{"attack", "sleep"});
  }

  /**
  * Gets the name of the Wizard Mob. 
  * @return    returns the String "Wizard"
  */
  public String getName() {
    return "Wizard";
  }

  /**
  * Overrides randomMove() in the Mob class but calls randomMove() from the Mob class and adds the Wizard's special moves to the choices avaliable to be randomly choosen.
  * @param mob    Mob being attacked
  * @return    returns a String of the attack used
  */
  public String randomMove(Mob mob) {
    String move = super.randomMove(mob);
    if(move.equals("sleep")) {
      sleep(mob);
    }
    return move;
  }

  /**
  * Base damage is 1, but chance for Mob to be poisoned.
  * @param mob    Mob being attacked
  */
  public void sleep(Mob mob) {
    int chance = (int) (Math.random() * 100);
    if(chance < 65) {
      mob.setStatus("sleep");
      mob.setCanMove(false);
      if(mob.getName().equals("You!")) {
        System.out.println("Zzzzz... the Wizard put you to sleep.");
      }
    }
  }

}