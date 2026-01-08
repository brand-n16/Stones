import becker.robots.*;
import java.awt.Color;
public class Selector extends Robot{
    //private fields
    //Constructor
    public Selector(City c, int s, int a, Direction d, int b) 
    {
        super(c,s,a,d,b);
    }
    //public methods
    public void turnRight()
    {
        for(int i = 0; i <3; i++) { super.turnLeft(); }
    }
    
    /*public boolean canPickThing(Color c)
    {
        if (this.getIntersection().countThings() > 0)
        {
            if( this.getIntersection().examineThings().next().getColor().equals(c) ) { return true; }
            else { return false; }
        }
        else
        {
            System.out.println("There are no objects to pick up");
            return false;
        }
    }*/
    
    //make the selector indestructible
    public void pickThing()
    {
        if ( super.canPickThing() ) { super.pickThing(); }
        else { System.out.println("Please move to a square with a stone."); }
    }

    public void putThing()
    {
        if ( super.countThingsInBackpack() > 0 ) { super.putThing(); }
        else { System.out.println("Please pick up a stone first."); }
    }

    public void move()
    {
        if ( super.frontIsClear() ) { super.move(); }
        else { System.out.println("Cannot move there."); }
    }
    
    
}