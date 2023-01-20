import java.lang.Math;

/**
* Goblin class, subclass of Mob. Special move is poison dart. 
*/
public class Goblin extends Mob {
  
  /**
  * Contructor for Goblin, calls the superclass Mob Constructor to set instance variables. Default health and is 20 and default attackPower is 2.
  */
  public Goblin() {
    super(20, 2, "none", new String[]{"attack", "poison dart"});
  }

  /**
  * Gets the name of the Goblin Mob. 
  * @return    returns the String "Goblin"
  */
  public String getName() {
    return "Goblin";
  }
  /**
  * Overrides randomMove() in the Mob class but calls randomMove() from the Mob class and adds the Goblins's special moves to the choices avaliable to be randomly choosen.
  * @param mob    Mob being attacked
  * @return    returns a String of the attack used
  */
  public String randomMove(Mob mob) {
    String move = super.randomMove(mob);
    if(move.equals("poison dart")) {
      poisonDart(mob);
    }
    return move;
  }

  /**
  * Base damage is 1, but chance for Mob to be poisoned.
  * @param mob    Mob being attacked
  */
  public void poisonDart(Mob mob) {
    mob.setHealth(mob.getHealth()-1);
    if(getStatus().equals("dead")) return;

    int chance = (int) (Math.random() * 100);
    if(chance < 40) {
      mob.setStatus("poison");
      if(mob.getName().equals("You!")) {
        System.out.println("Yikes! You've been poisoned!");
      }
    }
  }

}