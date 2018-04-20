package towers;

public class Bomb extends Towers {

    public Bomb(double xPos, double yPos, double reloadTime, double angleOfRotation) {
        super(xPos, yPos, reloadTime, angleOfRotation);
        this.buyingCost = newbuyingCost;
        this.refundValue = newRefundValue;
        this.reloadTime = newReloadTime;
        this.power = newPower;
        this.range = newRange;
        this.towerType = type.Bomb;
    }
    //los atributos estan puestos al azar
    private static int newbuyingCost = 200;
    private static int newRefundValue = 180;
    private static double newReloadTime = 2;
    private static double newRange = 80;
    private static double newPower = 0.9;

}
