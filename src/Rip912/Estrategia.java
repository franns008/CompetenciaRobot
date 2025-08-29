package Rip912;

public interface Estrategia {

    void onScannedRobot();

    void onHitByBullet();

    void onHitWall();

    void run();
}


