package Rip912;

public interface Estratega {
    Estrategia  onScannedRobot();

    Estrategia  onHitByBullet();

    Estrategia onHitWall();

    Estrategia run();
}
