import becker.robots.*;
public class RedStone extends Thing {
    public RedStone(City c, int s, int a) {
        super(c,s,a);
        //change the icon to a red stone
        setIcon(new RedCircleIcon(1)); 
    }
}