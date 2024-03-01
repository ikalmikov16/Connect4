import java.util.*;

public class Board
{
  //Instance Variables
  private Token[][] board;

  //Constructor method
  public Board()
  {
    board = new Token[6][7];

    for (int r = 0; r < board.length; r++)
      for (int c = 0; c < board[r].length; c++)
        board[r][c] = new Token();
  }

  /*This method will display the Connect4 board in theformat of:
  | | | | | | | |
  | | | | | | | |
  | | | | | | | |
  | | | | | | | |
  | | | | | | | |
  | | | | | | | |
   0 1 2 3 4 5 6
  */
  public void displayBoard()
  {
    for (int r = 0; r < board.length; r++)
    {
      System.out.print("|");
      for (int c = 0; c < board[r].length; c++)
      {
        System.out.print(board[r][c] + "|");
      }
      System.out.println();
    }

    for (int col = 0; col < board[0].length; col++)
      System.out.print(" " + col);

    System.out.println("\n");
  }

  public boolean dropToken(int col, String color)
  {
    if (col < 0 || col > 6)
    {
      return false;
    }

    for (int row = board.length - 1; row >= 0; row--)
      if (!board[row][col].isInitialized())
      {
        board[row][col].setColor(color);
        return true;
      }
    return false;
  }

  public boolean allInitialized()
  {
    for (int r = 0; r < board.length; r++)
      for (int c = 0; c < board[0].length; c++)
        if (!board[r][c].isInitialized())
          return false;
    return true;
  }

  public boolean checkWin()
  {
    if (checkWinHorizontal() || checkWinVertical() ||
    checkWinDiagonal1()|| checkWinDiagonal2())
      return true;
    return false;
  }

  private boolean checkWinHorizontal()
  {
    boolean win = false;
    for (int r = 0; r < board.length; r++)
      for (int c = 3; c < board[r].length; c++)
      {
        for (int i = c-3; i < c; i++)
        {
          if (board[r][i].equalsTo(board[r][i+1]))
              win = true;
          else
          {
            win = false;
            i = c;
          }
        }
        if (win)
          return win;
       }
     return win;
  }

  private boolean checkWinVertical()
  {
    boolean win = false;
    for (int c = 0; c < board[0].length; c++)
      for (int r = 3; r < board.length; r++)
      {
        for (int i = r-3; i < r; i++)
        {
          if (board[i][c].equalsTo(board[i+1][c]))
              win = true;
          else
          {
            win = false;
            i = r;
          }
        }
        if (win)
          return win;
       }
     return win;
  }

  private boolean checkWinDiagonal1()
  {
    boolean win = false;
    for (int r = 0; r < board.length - 3; r++)
      for (int c = 0; c < board[r].length - 3; c++)
      {
        for (int i = 0; i < 3; i++)
        {
          if (board[r+i][c+i].equalsTo(board[r+i+1][c+i+1]))
              win = true;
          else
          {
            win = false;
            i = 4;
          }
        }
        if (win)
          return win;
       }
     return win;
  }

  private boolean checkWinDiagonal2()
  {
    boolean win = false;
    for (int r = 3; r < board.length; r++)
      for (int c = 0; c < board[r].length - 3; c++)
      {
        for (int i = 0; i < 3; i++)
        {
          if (board[r-i][c+i].equalsTo(board[r-i-1][c+i+1]))
              win = true;
          else
          {
            win = false;
            i = 4;
          }
        }
        if (win)
          return win;
       }
     return win;
  }

  public int smartCol(int counter)
  {
    String x = "X";

    if(counter == 1 && !board[5][3].isInitialized())
      return 3;
    else if (connect(3,x) > -1)
      return connect(3,x);
    else if (connect(3,"O") > -1)
      return connect(3,"O");
    else if (connect(2,x) > -1)
      return connect(2,x);
    else if (connect(2,"O") > -1)
      return connect(2,"O");
    else if (connect(1,x) > -1)
      return connect(1,x);
    return (int)(Math.random()*7);
  }

  private int connect(int num, String x)
  {
    ArrayList<Integer> options = new ArrayList<>();

    options.add((Integer)checkConnectVertical(num, x));
    options.add((Integer)checkConnectDiagonal1R(num,x));
    options.add((Integer)checkConnectDiagonal2L(num,x));
    options.add((Integer)checkConnectDiagonal1L(num,x));
    options.add((Integer)checkConnectDiagonal2R(num,x));
    options.add((Integer)checkConnectHorizontal1(num,x));
    options.add((Integer)checkConnectHorizontal2(num,x));

    for (int i = 0; i < options.size(); i++)
      if ((int)options.get(i) == -1)
      {
        options.remove(i);
        i--;
      }

    if(options.size() == 0)
      return -1;
    return options.get((int)(Math.random()*options.size()));
  }

  private int checkConnectVertical(int num, String x)
  {
    boolean connect = false;
    for (int c = 0; c < board[0].length; c++)
      for (int r = board.length-1; r > 2; r--)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r-1][c].isInitialized())
            return c;
          else if (i == num - 1 && connect)
          {
            if (!board[r-i-1][c].isInitialized())//check if empty spot
              return c;
          }

          else if (!board[r-i][c].getColor().equals(x) ||
          !board[r-i][c].equalsTo(board[r-i-1][c]))
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

  //horizontal right
  private int checkConnectHorizontal1(int num, String x)
  {
    boolean connect = false;
    for (int r = board.length-1; r > 0; r--)
      for (int c = 0; c < board[r].length - 3; c++)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r][c+1].isInitialized())
            return c+1;
          else if (i == num - 1 && connect)
          {
            if (!board[r][c+i+1].isInitialized() &&
            (r == board.length - 1 || board[r][c+i+1].isInitialized()))//check if empty spot
              return c+i+1;
          }
          else if (!board[r][c+i].getColor().equals(x) ||
          !board[r][c+i].equalsTo(board[r][c+i+1]))
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

//horrizontal left
  private int checkConnectHorizontal2(int num, String x)
  {
    boolean connect = false;
    for (int r = board.length-1; r > 0; r--)
      for (int c = board[0].length - 1; c > 2; c--)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r][c-1].isInitialized())
            return c-1;
          else if (i == num - 1 && connect)
          {
            if (!board[r][c-i-1].isInitialized() &&
            (r == board.length - 1 || board[r][c-i-1].isInitialized()))
              return c-i-1;
          }
          else if (!board[r][c-i].getColor().equals(x) ||
          !board[r][c-i].equalsTo(board[r][c-i-1]))
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

  private int checkConnectDiagonal1R(int num, String x)
  {
    boolean connect = false;
    for (int r = board.length - 1; r > 2; r--)
      for (int c = 0; c < board[0].length - 4; c++)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r-1][c+1].isInitialized() && board[r][c+1].isInitialized())
            return c+1;
          else if (i == num - 1 && connect)
          {
            if (!board[r-i-1][c+i+1].isInitialized() && //check if empty spot
            board[r-i][c+i+1].isInitialized())//check if spot under is full
              return c+i+1;
          }
          else if (!board[r-i][c+i].getColor().equals(x) ||
          !board[r-i][c+i].equalsTo(board[r-i-1][c+i+1]))
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

  private int checkConnectDiagonal1L(int num, String x)
  {
    boolean connect = false;
    for (int r = 0; r < board.length-3; r++)
      for (int c = 3; c < board[0].length; c++)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r+1][c-1].isInitialized() && board[r+2][c-1].isInitialized())
            return c-1;
          else if (i == num - 1 && connect)
          {
            if (!board[r+i+1][c-i-1].isInitialized() && //check if empty spot
            (r == 2 || board[r+i+2][c-i-1].isInitialized()))//check if spot under is full
              return c-i-1;
          }
          else if (!board[r+i][c-i].getColor().equals(x) || //check if next spot
          !board[r+i][c-i].equalsTo(board[r+i+1][c-i-1]))   //is the same
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

  private int checkConnectDiagonal2L(int num, String x)
  {
    boolean connect = false;
    for (int r = board.length - 1; r > 2; r--)
      for (int c = 3; c < board[0].length - 1; c++)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r-1][c-1].isInitialized() && board[r][c-1].isInitialized())
            return c-1;
          else if (i == num - 1 && connect)
          {
            if (!board[r-i-1][c-i-1].isInitialized() && //check if empty spot
            board[r-i][c-i-1].isInitialized())//check if spot under is full
              return c-i-1;
          }
          else if (!board[r-i][c-i].getColor().equals(x) && //check if next spot
          !board[r-i][c-i].equalsTo(board[r-i-1][c-i-1]))   //is the same
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

  private int checkConnectDiagonal2R(int num, String x)
  {
    boolean connect = false;
    for (int r = 0; r < 3; r++)
      for (int c = 0; c < board[0].length - 3; c++)
      {
        for (int i = 0; i < num; i++)
        {
          if(num == 1 && board[r][c].getColor().equals(x) &&
          !board[r+1][c+1].isInitialized() && board[r+2][c+1].isInitialized())
            return c+1;
          else if (i == num - 1 && connect)
          {
            if (!board[r+i+1][c+i+1].isInitialized() && //check if empty spot
            (r == 2 || board[r+i+2][c+i+1].isInitialized()))//check if spot under is full
              return c+i+1;
          }
          else if (!board[r+i][c+i].getColor().equals(x) || //check if next spot
          !board[r+i][c+i].equalsTo(board[r+i+1][c+i+1]))   //is the same
            i = num;

          connect = true;
        }
        connect = false;
       }
     return -1;
  }

  /*private boolean boardIsClear()
  {
    for (int r = 0; r < board.length; r++)
      for (int c = 0; c < board[0].length; c++)
        if (board[r][c].isInitialized())
          return false;
    return true;
  }*/

  public void legendaryDrop(int counter)
  {
    if (counter == 1)
    {
      board[5][0].setColor("X");
      System.out.println("The CPU dropped a token in collumn 0");
    }

    else if (counter == 3)
    {
      board[5][2].setColor("X");
      System.out.println("The CPU dropped a token in collumn 2");
    }
    else if (counter == 5)
    {
      board[5][1].setColor("X");
      System.out.println("The CPU dropped a token in collumn 1");
    }
    else
    {
      board[5][3].setColor("X");
      System.out.println("The CPU dropped a token in collumn 3");
    }

  }
}
