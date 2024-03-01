import java.util.*;

public class Token
{
  //Instance Variables
  private String color;
  private boolean isInitialized;

  //Constructor Method
  public Token()
  {
    isInitialized = false;
    color = "";
  }

  public Token(String color)
  {
    this.color = color;
    isInitialized = true;
  }

  //Change the color of the Token
  //X is red
  //O is yellow
  public void setColor(String color)
  {
    this.color = color;
    isInitialized = true;
  }

  //
  public String getColor()
  {
    return color;
  }

  public boolean isInitialized()
  {
    return isInitialized;
  }

  public boolean equalsTo(Token other)
  {
    if (color.equals(other.getColor())
    && isInitialized && other.isInitialized())
      return true;
    return false;
  }

  public String toString()
  {
    if (isInitialized)
      return color;
    return " ";
  }
}
