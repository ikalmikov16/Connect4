import java.util.*;

public class Connect4
{
  public static void main(String[] args)
  {
    int user = 0;
    Scanner input = new Scanner(System.in);

    System.out.println("\nWelcome to Connect4!!!\n");

    while (user != 3)
    {
      System.out.println("Choose one of the following:\n1) Player vs Player \n2) Play vs CPU\n3) Exit \n");
      user = input.nextInt();

      if (user == 1)
      {
        System.out.print("Player 1, Enter Your Name: ");
        String p1 = input.next();

        System.out.print("\nPlayer 2, Enter Your Name: ");
        String p2 = input.next();

        System.out.println("\nNow we will beging to play!\nFirst to connect 4 Tokens wins");

        playGame(p1,p2, -1);
      }

      //Play against CPU
      if (user == 2)
      {
        int AIlevel = getAILevel();

        System.out.print("Enter Your Name: ");
        String p1 = input.next();

        System.out.println("\nNow we will begin to play!\n"+
        "First to connect 4 Tokens wins");

        playGame(p1,"CPU", AIlevel);
    }
  }
    System.out.println("Goodbye :))\n");
}

  //Methods//////////////////////////

  private static void playGame(String p1, String p2, int level)
  {
    int counter = (int)(Math.random()*2)-1;
    if (level == 3)
      counter = 0;
    boolean win = false;
    boolean CPU = p2.equals("CPU");
    Board game = new Board();

    game.displayBoard();

    while (!win)
    {
      counter++;

      if (counter % 2 == 0)
      {
        playerMove(p1, game, "O");

        if(game.allInitialized())
        {
          win = true;
          break;
        }
        else
          win = game.checkWin();
        System.out.println();
      }
      else
      {
        if (!p2.equals("CPU"))
          playerMove(p2, game, "X");
        else
          AIMove(game, level, counter);

        if(game.allInitialized())
        {
          win = true;
          break;
        }
        else
          win = game.checkWin();
        System.out.println();
      }
      game.displayBoard();
    }

    //Display result
    if (game.allInitialized() && !game.checkWin())
      System.out.println("It's a tie.\n");
    else if (counter % 2 == 0)
      System.out.println("Good Job " + p1 + ", You Won!!!\n");
    else if(CPU)
      System.out.println("The CPU won!! You're trash.\n");
    else
      System.out.println("Good Job " + p2 + ", You Won!!!\n");
  }

  private static void playerMove(String playerName, Board game, String token)
  {
    Scanner input = new Scanner(System.in);
    boolean x;

    do
    {
      System.out.print(playerName + ", choose a collumn to drop your Token: ");
      int col = input.nextInt();
      x = game.dropToken(col,token);

    } while (!x);
  }

  private static void AIMove(Board game, int level, int counter)
  {
    boolean x;
    int col = 0;
    do
    {
      if (level == 1)
      {
        col = (int)(Math.random() * 7);
        x = game.dropToken(col,"X");
      }
      else if (level == 2)
      {
        col = game.smartCol(counter);
        x = game.dropToken(col,"X");
      }
      else
      {
        game.legendaryDrop(counter);
        x = true;
      }

    } while (!x);

    if (level != 3)
      System.out.println("The CPU dropped a token in collumn " + col);
  }

  private static int getAILevel()
  {
    Scanner input = new Scanner(System.in);

    System.out.println("Choose your AI Level:\n"+
    "1) Dumb\n"+
    "2) Smart\n"+
    "3) Legendary");

    return input.nextInt();
  }
}
