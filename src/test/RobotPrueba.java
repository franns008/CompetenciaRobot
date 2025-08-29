package test;

import robocode.JuniorRobot;

public class RobotPrueba extends JuniorRobot {
    private Estrategia estrategia;

    public RobotPrueba(Estrategia e){

    }

    public RobotPrueba(){

    }

    public void setEstrategia(Estrategia estrategia) {
        this.estrategia = estrategia;
    }

    public void onScannedRobot() {
        this.estrategia.onScannedRobot();;
    }

    @Override
    public void onHitByBullet() {
        this.estrategia.onHitByBullet();
    }

    @Override
    public void onHitWall() {
        this.estrategia.onHitWall();
    }
}
