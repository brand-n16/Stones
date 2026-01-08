import becker.robots.*;
import java.util.*;
import java.lang.Math;
import java.awt.Color;
public class main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        SelectorIconBlue sIB = new SelectorIconBlue(0.9);
        SelectorIconRed sIR = new SelectorIconRed(0.9);
        String input, currentTurn;
        int pickUpPosX = 0, pickUpPosY = 0, turn = 0;
        boolean winner = false;
        //Color redColor = Color.RED, blueColor = Color.BLUE, currentTurn;
        //set up the board (and fill it with zeros)
        City board = new City(4,4);
        //set up the 2d array to keep track of the board
        String[][] boardArray = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardArray[i][j] = "0"; 
            }
        }
        //set up the surrounding boundary
        for (int i = 0; i < 4; i++) 
        {
            new Wall(board, 0, i, Direction.NORTH); // top
            new Wall(board, 3, i, Direction.SOUTH); // bottom
        }
        for (int i = 0; i < 4; i++) 
        {
            new Wall(board, i, 0, Direction.WEST); // left
            new Wall(board, i, 3, Direction.EAST); // right
        }
        //set up the stones
        for (int i = 0; i < 4; i += 2) 
        {
            //left
            new RedStone(board, i, 0); 
            boardArray[i][0] = "Red";
            new BlueStone(board, i+1, 0);
            boardArray[i+1][0] = "Blue";
            //right
            new RedStone(board, i+1, 3);
            boardArray[i+1][3] = "Red";
            new BlueStone(board, i, 3);
            boardArray[i][3] = "Blue";
        }
        //set up the selector
        Selector selector = new Selector (board, 3 , 0, Direction.NORTH, 0);
        //welcome the user
        System.out.println("Hello! Welcome to stones!");
        System.out.println("Players take turns moving their coloured stones one space vertically/ horizontally"); 
        System.out.println("Whoever gets 3 in a row wins!");
        System.out.println("The selector is the big red square in the bottom left corner");
        System.out.println("The color of the selector tells you whose turn it is");
        System.out.println("Enter W/A/S/D to move the selector (Caps lock would be useful)");
        System.out.println("Use the E key to pick up a stone");
        System.out.println("Use the / key to exit");
        System.out.println("Red goes first"); 
        do
        {
            if (turn % 2 == 0)
            {
                selector.setIcon(sIR);
                currentTurn = "Red";
            }
            else 
            {
                selector.setIcon(sIB);
                currentTurn = "Blue";
            }
            input = in.nextLine();
            switch(input) 
            {
                case "W":
                    selector.move();
                    break;
                case "A":
                    selector.turnLeft();
                    selector.move();
                    selector.turnRight();
                    break;
                case "S":
                    selector.turnLeft();
                    selector.turnLeft();
                    selector.move();
                    selector.turnRight();
                    selector.turnRight();
                    break;
                case "D":
                    selector.turnRight();
                    selector.move();
                    selector.turnLeft();
                    break;
                case "E":
                    if(selector.countThingsInBackpack() > 0)
                    {
                        System.out.println("You have already picked up a stone.");
                    }
                    else if(boardArray[selector.getStreet()][selector.getAvenue()].equals(currentTurn))
                    {
                        selector.pickThing();
                        pickUpPosX = selector.getStreet();
                        pickUpPosY = selector.getAvenue();
                        boardArray[pickUpPosX][pickUpPosY] = "0";
                        System.out.println("You have picked up a stone");
                        System.out.println("Use the R key to place down your stone");
                    }
                    else
                    {
                        System.out.println("Please pick up a " + currentTurn + " stone");
                    }
                    break;
                case "R":
                    if( ( pickUpPosX == selector.getStreet() ) && ( pickUpPosY == selector.getAvenue() ) )//if the stone is not moved at all, give another chance
                    {
                        if (turn % 2 == 0)
                        {
                            boardArray[selector.getStreet()][selector.getAvenue()] = "Red";
                        }
                        else 
                        {
                            boardArray[selector.getStreet()][selector.getAvenue()] = "Blue";
                        }
                        selector.putThing();
                        System.out.println("It seems like you have not moved your stone. Please move your stone or choose another stone to move.");
                    }
                    else if( Math.abs( pickUpPosX - selector.getStreet() ) > 0  && Math.abs( pickUpPosY - selector.getAvenue() ) > 0) //if stone is moved vertically and horizontally
                    {
                        System.out.println("Cannot move your stone diagonally");
                    }
                    else if( Math.abs( pickUpPosX - selector.getStreet() ) > 1 || Math.abs(pickUpPosY - selector.getAvenue() ) > 1 ) //if stone is moved more than 2 units
                    {
                        System.out.println("Please move your stone a maximum of 1 unit");
                    }
                    else if( selector.getIntersection().countThings() > 0 ) 
                    {
                        //if the selector is on a corner, then we need to consider the 2 walls
                        if ((selector.getStreet() == 0 && selector.getAvenue() == 0) || (selector.getStreet() == 0 && selector.getAvenue() == 3) || (selector.getStreet() == 3 && selector.getAvenue() == 0) || (selector.getStreet() == 3 && selector.getAvenue() == 3))
                        {
                            if (selector.getIntersection().countThings() > 2) 
                            {
                                System.out.println("There is already a stone here");
                            }
                            else
                            {
                                if (turn % 2 == 0)
                                {
                                    boardArray[selector.getStreet()][selector.getAvenue()] = "Red";
                                }
                                else 
                                {
                                    boardArray[selector.getStreet()][selector.getAvenue()] = "Blue";
                                }
                                selector.putThing();
                                turn += 1;
                            }
                        }
                        // if the selector is not on the corner, but still on an edge, we need to consider the one wall (this has to go after)
                        else if (selector.getStreet() == 0 || selector.getStreet() == 3 || selector.getAvenue() == 0 || selector.getAvenue() == 3)
                        {
                            if (selector.getIntersection().countThings() > 1)
                            {
                                System.out.println("There is already a stone here");
                            }
                            else
                            {
                                if (turn % 2 == 0)
                                {
                                    boardArray[selector.getStreet()][selector.getAvenue()] = "Red";
                                }
                                else 
                                {
                                    boardArray[selector.getStreet()][selector.getAvenue()] = "Blue";
                                }
                                selector.putThing();
                                turn += 1;
                            }
                        }
                        else
                        {
                            System.out.println("There is already a stone here");
                        }
                    }
                    else
                    {
                        if (turn % 2 == 0)
                        {
                            boardArray[selector.getStreet()][selector.getAvenue()] = "Red";
                        }
                        else 
                        {
                            boardArray[selector.getStreet()][selector.getAvenue()] = "Blue";
                        }
                        selector.putThing();
                        turn += 1;
                    }
                    break;
                case "/":
                    System.out.println("Thanks for playing! Come again soon!");
                    break;
                default:
                    System.out.println("Invalid input, please enter W/A/S/D or E/R");
            } 
            /*for(int row = 0; row < 4; row++)
            {
                for (int col = 0; col < 4; col++)
                {
                    System.out.println(boardArray[row][col]);
                }
            }*/ //this loop was used to print the array
            for(int row = 0; row < 4; row++)
            {
                for (int col = 0; col < 4; col++)
                {
                    //skip empty spots
                    if (boardArray[row][col] == "0")
                    {
                        continue;
                    }
                    //horizontal
                    if (col + 2 < 4 && boardArray[row][col] == boardArray[row][col + 1] && boardArray[row][col] == boardArray[row][col + 2])
                    {
                        winner = true;
                        //System.out.println("horiz win along row " + row);
                    }
                    //vertical
                    if (row + 2 < 4 && boardArray[row][col] == boardArray[row + 1][col] && boardArray[row][col] == boardArray[row + 2][col])
                    {
                        winner = true;
                        //System.out.println("vert win along col " + col);
                    }
                    //diagonal up
                    if (row + 2 < 4 && col + 2 < 4 && boardArray[row][col] == boardArray[row + 1][col + 1] && boardArray[row][col] == boardArray[row + 2][col + 2])
                    {
                        winner = true;
                        //System.out.println("SE win starting at " + row + ", " + col);
                    }
                    //diagonal down
                    if (row + 2 < 4 && col - 2 >= 0 && boardArray[row][col] == boardArray[row + 1][col - 1] && boardArray[row][col] == boardArray[row + 2][col - 2])
                    {
                        winner = true;
                        //System.out.println("NE win starting at " + row + ", " + col);
                    }
                }
            }
            if (winner == true)
            {
                if (turn % 2 == 0)
                {
                    System.out.println("BLUE WINS!");
                }
                else
                {
                    System.out.println("RED WINS!");
                }
                System.out.println("Press / to exit the game");
            }
        } while (!input.equals("/"));
    }
}
