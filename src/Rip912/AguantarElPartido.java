package Rip912;

import robocode.JuniorRobot;
import robocode.util.Utils;

public class AguantarElPartido implements Estrategia{
    private JuniorRobot robot;
    private boolean onCorner;
    private int hitByBulletCounter = 0, goingBack = 1;

    public AguantarElPartido(JuniorRobot robot) {
        this.robot = robot;
    }

    private double getHipotenusa(double catA, double catB){
        return Math.sqrt(
                (Math.pow(catA,2)+Math.pow(catB,2))
        );
    }

    private double chooseACorner() {
        // 0 =  inf-izq , 1 = inf-der, 2 = sup-izq, 3 = sup-der
        double distanceFromNorthWall = robot.fieldHeight - robot.robotY;
        double distanceFromSouthWall = robot.robotY;
        double distanceFromEastWall = robot.fieldWidth - robot.robotX;
        double distanceFromWestWall = robot.robotX;
        double[] arrayOfDistances = {
                getHipotenusa(distanceFromWestWall, distanceFromSouthWall),
                getHipotenusa(distanceFromEastWall, distanceFromSouthWall),
                getHipotenusa(distanceFromWestWall, distanceFromNorthWall),
                getHipotenusa(distanceFromEastWall, distanceFromNorthWall)
        };
        int indexMin = 0;
        for (int i=0; i < arrayOfDistances.length; i++){
            System.out.println("La esquina "+ i +" esta a " + arrayOfDistances[i]);
            if (arrayOfDistances[i] < arrayOfDistances[indexMin]){
                indexMin = i;
            }
        }
        int[] angles = {225, 135, 315, 45};
        int angle = angles[indexMin];

        System.out.println("Elegi la esquina" + indexMin);

        robot.turnTo(angle);

        return arrayOfDistances[indexMin];

    }

    private double getAngleToShoot(){
        double angleToEnemy = Math.toRadians(this.robot.heading) + Math.toRadians(this.robot.scannedBearing);
        double enemyX = this.robot.robotX + this.robot.scannedDistance * Math.sin(angleToEnemy);
        double enemyY = this.robot.robotY + this.robot.scannedDistance * Math.cos(angleToEnemy);

        // Datos útiles del enemigo
        double enemyHeading = this.robot.scannedHeading;   // en grados
        double enemyVelocity = this.robot.scannedVelocity; // positivo adelante, negativo atras

        // Calcular el ángulo futuro para disparar

        double angleToFire = calcularAnguloDeDisparo(
                this.robot.robotX, this.robot.robotY,
                enemyX, enemyY,
                enemyVelocity, enemyHeading
        );

        // Apuntar el cañón hacia ese ángulo

        double gunTurn = Utils.normalRelativeAngleDegrees(angleToFire - robot.gunHeading);
        return gunTurn;
    }

    @Override
    public void onScannedRobot() {
        double angleToFire = getAngleToShoot();

        // Apuntar el cañón hacia ese ángulo
        double gunTurn = Utils.normalRelativeAngleDegrees(angleToFire);
        robot.turnGunRight((int)gunTurn);
        if (robot.scannedDistance < 100) {
            robot.fire(3);
        }
        else if (robot.scannedDistance < 200) {
            robot.fire(2);
        }
    }

    public double calcularAnguloDeDisparo(
            double miX, double miY,
            double enemigoX, double enemigoY,
            double enemigoVel, double enemigoHeading) {
        // Tiempo que tarda la bala en recorrer la distancia actual


        // Predicción de posición futura del enemigo
        double futuroX = enemigoX + Math.sin(Math.toRadians(enemigoHeading)) * enemigoVel ;
        double futuroY = enemigoY + Math.cos(Math.toRadians(enemigoHeading)) * enemigoVel ;

        // Calcular ángulo desde mi posición hasta la posición futura
        double dx = futuroX - miX;
        double dy = futuroY - miY;

        double angulo = Math.toDegrees(Math.atan2(dx, dy));

        // Normalizar entre 0°–360°
        if (angulo < 0) {
            angulo += 360;
        }

        return angulo;
    }

    @Override
    public void onHitByBullet() {
        hitByBulletCounter++;
        System.out.println("Me dieron");
        if (hitByBulletCounter % 3 == 0){
            System.out.println("No aguanto más");
            double attackingAngle = getAngleToShoot();
            robot.ahead(50);
            robot.turnGunRight((int)attackingAngle);
            onCorner = false;
        }
    }

    @Override
    public void onHitWall() {
        this.onCorner = !this.onCorner;
        robot.turnTo(robot.hitWallAngle + 180);
    }

    @Override
    public void run() {
        onCorner = false;

        while (true){
            double distance = chooseACorner();
            robot.ahead((int) distance);
            while (onCorner){

                if ((robot.gunHeading == 0) || (robot.gunHeading == 90)
                        || (robot.gunHeading == 180) || (robot.gunHeading == 270)){
                    goingBack = goingBack * -1;
                }
                robot.turnGunTo(robot.gunHeading + (5 * goingBack));
            }
        }
    }
}
