import becker.robots.*;
public class BlueStone extends Thing{
    public BlueStone(City c, int s, int a) {
        super(c,s,a);
        //change the icon to a blue stone
        setIcon(new BlueCircleIcon(1));
    }
}