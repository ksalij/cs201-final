import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* The Game class which is the class that is called to play the game. 
* @author Kiri Salij
* @version 3/15/21
*/
public class Game {
  private UnweightedGraph dungeon;
  private Map<Integer, Mob> roomMob;
  private Player player;
  private boolean gameOver = false;

  /**
  * Constructor for the Game class. Initializes the instance variables. 
  */
  public Game() {
    dungeon = new MysteryUnweightedGraphImplementation();
    roomMob = new HashMap<Integer, Mob>();
    player = new Player();
  }

  /**
  * Default text and traversal of rooms for the game. Called from the dungeon method after the dungeon has been populated. 
  * @param numRooms    int of number of rooms in the dungeon
  */
  public void defaultGame(int numRooms) {
    Scanner scan = new Scanner(System.in);
    int roomCurr = 0;
    while(gameOver == false) {
      System.out.println("\n\nYou have entered Room: " + roomCurr);
      if(roomCurr == numRooms-1) {
        System.out.println("    You enter a room considerably bigger than all the previous rooms. You walk into the middle of this massive chamber and see the Dragon curled up asleep surrounded by treasure of all types. You see coins and goblets and necklaces and even a beautiful stained glass window. In the corner you see the princess hunched over, quietly sobbing into her hands. If you try to make a run for it with the princess, you know that you will never be able to escape this twisting maze of rooms before the Dragon wakes up and catches you both. So you charge the beast to kill him before he wakes up!");
      }
      else if(roomMob.get(roomCurr).getStatus().equals("dead")) {
        System.out.println("The " + roomMob.get(roomCurr).getName() + " in this room is already dead.");
      }
      else {
        System.out.println("Oh no! There is a " + roomMob.get(roomCurr).getName() + " that's about to attack you!");
      }
      gameOver = fight(roomMob.get(roomCurr));
      if(gameOver == true) {
        break;
      }
      if(roomCurr == numRooms-1) {
        gameOver = true;
        break;
      }


      ArrayList<Integer> roomChoices = new ArrayList<Integer>();
      for(Integer neighbor: dungeon.getNeighbors(roomCurr)) {
        roomChoices.add(neighbor);
      }
      
      boolean correct = false;
      int newRoom = 0;

      while(correct == false) {
        boolean first = true;
        System.out.print("Do you choose door");
        for(Integer room: roomChoices) {
          if(first == true) {
            System.out.print(" " + room);
            first = false;
          }
          else {
            System.out.print(" or " + room);
          }
        }
        System.out.println("?");
        newRoom = Integer.parseInt(scan.nextLine());
        for(Integer room: roomChoices) {
          if(newRoom == room) {
            correct = true;
            break;
          }
        }
        if(correct == false) {
          System.out.println("Please choose one of the options.");
        }
      }

      roomCurr = newRoom;
    }
    
    if(won()) {
      System.out.println("You won! You saved the princess!");
    }
  }


  /**
  * Populates the dungeon with a test rooms and Mobs.
  */
  public void MVPdungeon() {
    for(int i = 0; i < 4; i++) {
      dungeon.addVertex();
    }
    dungeon.addEdge(0,1);
    dungeon.addEdge(0,2);
    dungeon.addEdge(1,2);
    dungeon.addEdge(2,3);
    roomMob.put(0, new Mob());
    roomMob.put(1, new Goblin());
    roomMob.put(2, new Wizard());
    roomMob.put(3, new Dragon());
    defaultGame(4);
  }

  /**
  * Populates the dungeon Graph with vertices and edges that are the rooms and doors between rooms. Puts random Mobs in a HashMap that corresponds to the room numbers. 
  */
  public void dungeon1() {
    int numRooms = 10;
    for(int i = 0; i < numRooms; i++) {
      dungeon.addVertex();
    }
    dungeon.addEdge(0,1);
    dungeon.addEdge(0,2);
    dungeon.addEdge(0,3);
    dungeon.addEdge(1,4);
    dungeon.addEdge(1,6);
    dungeon.addEdge(2,4);
    dungeon.addEdge(2,1);
    dungeon.addEdge(3,6);
    dungeon.addEdge(3,5);
    dungeon.addEdge(3,2);
    dungeon.addEdge(4,8);
    dungeon.addEdge(4,9);
    dungeon.addEdge(5,7);
    dungeon.addEdge(5,8);
    dungeon.addEdge(6,7);
    dungeon.addEdge(6,1);
    dungeon.addEdge(7,5);
    dungeon.addEdge(8,9);

    for(int i = 0; i < numRooms-1; i++) {
      int choice = (int) (Math.random() * (3));
      if(choice == 0) {
        roomMob.put(i, new Mob());
      }
      else if(choice == 1) {
        roomMob.put(i, new Goblin());
      }
      else if(choice == 2) {
        roomMob.put(i, new Wizard());
      }
      
    }
    roomMob.put(numRooms-1, new Dragon());
    defaultGame(numRooms);
  }

  /**
  * Method for the fighting interface. Loops until Mob or Player is dead.
  *@return returns boolean of whether the player died or not. True if player is dead, false otherwise
  */
  public boolean fight(Mob mob) {
    printStats(mob);
    printStats(player);
    Scanner scan = new Scanner(System.in);
    while((player.getHealth() > 0) && (mob.getHealth() > 0)) {
      player.statusEffects();
      if(player.canMove()) {
        System.out.println("\nChoose: attack or heal");
        String playerMove = scan.nextLine();
        if(playerMove.equals("attack")) {
          player.attack(mob);
        }
        else if(playerMove.equals("heal")) {
          player.heal();
        }
        else {
          System.out.println("Invalid Move. Turn forfeited.");
        }
      }
      else {
        System.out.println("\nYou were asleep and unable to move this turn.");
      }
      printStats(mob);
      printStats(player);

      mob.statusEffects();
      if(mob.getStatus().equals("dead")) {
        System.out.println("\nYou killed the " + mob.getName() + "!");
        break;
      }
      //delays the output after the player's turn
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
      System.out.println("\nNow it's the " + mob.getName() + "'s turn:");
      if(mob.canMove()) {
        System.out.println("They choose " + mob.randomMove(player) + "!");
      }
      else {
        System.out.println("The " + mob.getName() + " is asleep.");
      }
      printStats(mob);
      printStats(player);

    }
    if(player.getStatus().equals("dead")) {
      System.out.println("\nYou died. Game Over.");
      return true;
    }
    else return false;
  }

  /**
  * Prints the Mob's stats with a nice line above.
  */
  public void printStats(Mob mob) {
    System.out.println("-------------------------------");
    System.out.println(mob);
  }

  /**
  * Checks if the Player is not dead and the game is over, in that case, return true. The game is won.
  * @return    boolean of whether the Player has won
  */
  public boolean won() {
    if(!player.getStatus().equals("dead") && (gameOver == true)) {
      return true;
    }
    else return false;
  }

  /**
  * Main method with with beginning and ending dialogue. Creates and instance of Game and calls the dungeon method of which dungeon is being traversed. Doesn't take any command line arguments.
  */
  public static void main(String[] args) {
    Game game = new Game();
    System.out.println("Karletonia: The First Adventure\n    You are a tired, weary adventurer traveling through the humid jungles of Karltonia. As you walk, you suddenly hear a shout from above.\n    \"Help! What the heck are you doing? Don't you see me getting carried away by this horrendious dragon?!\"\n    You look up to see a beautiful girl with long flowing blond hair dressed in a full-length powder blue gown being held in the talons of a massive crimson dragon. You notice something sparkling in the sun on the top of her head that looks like it could be a tiara. Before you can get a better look at her, the dragon dives into a hole in the foliage and into the ground. You are utterly shocked! Although you are travel-worn and tired, you swear to find this stunning girl and save her from that flying beast!\n    You look around and see hidden behind some vines, an opening that looks like it could be the enterance to a dragon's lair. Being the brave hero you are, you venture immediately inside.");
      try {
        TimeUnit.SECONDS.sleep(15);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    game.dungeon1();
    if(game.won()) {
      System.out.println("    You sigh with relief as the Dragon takes its last breath, and you are free of its deadly fire. You grab the princess and you find in the corner a ladder that will lead both of you back up to the surface.");
    }
    System.out.println("\n\nThe End.\n\n\nCredits:\nWritten by: Kiri Salij\nCoded by: Kiri Salij\nDeveloped by: Kiri Salij\n\nDedicated to Anya Vostinar");
  }
}