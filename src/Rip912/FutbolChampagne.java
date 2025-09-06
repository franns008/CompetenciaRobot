package Rip912;

import robocode.JuniorRobot;


public class FutbolChampagne implements Estrategia{

    private JuniorRobot robot;
    private boolean volver =false;
    private boolean volverArma =false;
    private boolean onAWall = false;
    private MapToWall myMap;
    private int cantDisparosRecibidos = 0;
    private int gunAngle = 0;
    private int cantToques = 0;
    private int movimientoCañon = 20;
    private int hitFromCrashCounter;

    public FutbolChampagne(JuniorRobot robot) {
        this.robot = robot;
    }

    private void chequearTodosLados(int limite){
        // Actualizar ángulo relativo del arma
        if (this.robot.others <= 7){
            this.movimientoCañon = 10;
        }

        if (volverArma) {
            gunAngle -= movimientoCañon;  // gira a la izquierda
        } else {
            gunAngle += movimientoCañon;  // gira a la derecha
        }
        // Limitar la rotación a ±90° relativo al cuerpo
        if (gunAngle >= limite) {
            volverArma = true;  // invertir dirección
        } else if (gunAngle <= 0) {
            volverArma = false; // invertir dirección
        }
    }

    private void posicionArmaEnPared(){
        if(this.robot.robotX == 0 && this.robot.robotY == 0){
            this.robot.bearGunTo(180);
            gunAngle = 180;
        } else if (this.robot.robotX == 0 && this.robot.robotY == this.robot.fieldHeight) {
            this.robot.bearGunTo(0);
            gunAngle = 0;
        }else if(this.robot.robotX == this.robot.fieldWidth && this.robot.robotY == 0){
            this.robot.bearGunTo(180);
            gunAngle = 180;
        }else{
            this.robot.bearGunTo(0);
            gunAngle = 0;
        }
    }

    private void chequearBloqueo(int posicionActualx,int posicionActualY){
        if(this.robot.robotY == posicionActualY && this.robot.robotX ==posicionActualx){
            hitFromCrashCounter += 1;
        }
        else {
            hitFromCrashCounter = 0;
        }
    }

    public void run() {
        // Que hago en cada turno que sea parte de la estrategia
        // Moverse hacia una pared
        // Elegir una pared
        // Si veo a un robot no me importa, estoy buscando una pared

        while (true){
            myMap = chooseAWall();
            int posicionY=this.robot.robotY;
            int posicionX=this.robot.robotX;
            while (!onAWall) {
                this.chequearBloqueo(posicionX,posicionY);
                if(hitFromCrashCounter==3) {
                    this.robot.turnBackLeft(90, 90);

                }
                this.chequearTodosLados(45);
                robot.turnGunTo(robot.heading + gunAngle);
                posicionY=this.robot.robotY;
                posicionX=this.robot.robotX;
                robot.ahead(15);
            }
            robot.turnRight(90);
            robot.turnGunRight(90);

            while (onAWall) {
                // Mover el robot
                this.chequearBloqueo(posicionX,posicionY);

                if(hitFromCrashCounter==2) {
                    onAWall = false;
                    this.robot.turnRight(90);
                    this.robot.ahead(80);

                }
                posicionY=this.robot.robotY;
                posicionX=this.robot.robotX;
                if (!volver) {
                    robot.ahead(35);
                } else {
                    robot.back(35);
                }
                this.chequearTodosLados(180);

                // Girar el arma al ángulo relativo

                robot.turnGunTo(robot.heading + gunAngle);
                if (hitFromCrashCounter == 3){
                    hitFromCrashCounter=0;
                    System.out.println("on wall "+onAWall);
                    onAWall = false;
                }
            }
        }

    }

    private MapToWall chooseAWall() {
        // 0 = Pared arriba, 1 = Pared derecha, 2 = Pared abajo, 3 = Pared izq
        double[] arrayOfDistances = {
                robot.fieldHeight - robot.robotY,
                robot.fieldWidth - robot.robotX,
                robot.robotY,
                robot.robotX
        };
        int indexMin = 0;
        for (int i=0; i < arrayOfDistances.length; i++){
            if (arrayOfDistances[i] < arrayOfDistances[indexMin]){
                indexMin = i;
            }
        }
        int angle = indexMin * 90;
        if (angle == 270) angle = -90;
        MapToWall map = new MapToWall((int)arrayOfDistances[indexMin], indexMin);
        robot.turnTo(angle);
        return map;
    }

    private void disparo(double daño){
        if (!onAWall) {
            this.robot.turnGunTo(this.robot.scannedAngle);
            this.robot.fire(daño);
        }
        if(onAWall){
            this.robot.fire(daño-0.2);
        }
    }
    @Override
    public void onScannedRobot() {
        /*if(this.robot.scannedDistance<250 || robot.others>5){
            this.disparo(2);
        }*/
        this.disparo(2);

    }

    @Override
    public void onHitByBullet() {
        cantDisparosRecibidos++;
        this.robot.turnGunTo(this.robot.hitByBulletAngle);
        this.robot.fire(2);
        if( (cantDisparosRecibidos % 3)==0) {
            this.robot.turnAheadRight(80,90);
            this.robot.ahead(50);
            onAWall = false;
        } // cuando me pegan 3 veces me muevo.
    }

    @Override
    public void onHitWall() {
        cantToques++;
        if(!onAWall){onAWall = true;}
        if(cantToques == 2){
            this.posicionArmaEnPared();
        }
        volver=!volver;
    }
}
