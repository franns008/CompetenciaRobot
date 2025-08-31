package Rip912;

import robocode.JuniorRobot;


public class futbolChampagne implements Estrategia{

    private JuniorRobot robot;
    private boolean stopWhenISeeARobot = false;
    private boolean volver =false;
    private boolean volverArma =false;
    private boolean onAWall = false;
    private MapToWall myMap;
    private int cantDisparosRecibidos = 0;
    private int gunAngle = 0;
    private boolean sincronizarArma =false;
    private int cantToques = 0;


    public futbolChampagne(JuniorRobot robot) {
        this.robot = robot;
    }

    private void chequearTodosLados(){
        // Actualizar ángulo relativo del arma
        if (volverArma) {
            gunAngle -= 10;  // gira a la izquierda
        } else {
            gunAngle += 10;  // gira a la derecha
        }

        // Limitar la rotación a ±90° relativo al cuerpo
        if (gunAngle >= 90) {
            gunAngle = 90;
            volverArma = true;  // invertir dirección
        } else if (gunAngle <= -90) {
            gunAngle = -10;
            volverArma = false; // invertir dirección
        }
    }

    private void mirarConPocos(){

    }

    public void run() {
        // Que hago en cada turno que sea parte de la estrategia
        // Moverse hacia una pared
        // Elegir una pared
        // Si veo a un robot no me importa, estoy buscando una pared
        stopWhenISeeARobot = false;
        while (true){
            myMap = chooseAWall();
            // Ahora si
            stopWhenISeeARobot = true;
            // Moverse hacia ella
            while (!onAWall) {
                robot.ahead(10);
            }

            robot.turnRight(90);
            robot.turnGunRight(90);

            // Ángulo relativo al cuerpo
            boolean volverArma = false;

            while (onAWall) {
                // Mover el robot
                if (!volver) {
                    robot.ahead(50);
                } else {
                    robot.back(50);
                }
                this.chequearTodosLados();

                // Girar el arma al ángulo relativo
                robot.turnGunTo(robot.heading + gunAngle);
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

    @Override
    public void onScannedRobot() {
        if (!stopWhenISeeARobot){
            return;
        }
        if (!onAWall) {
            //int angulo = this.robot.gunBearing;
            //this.robot.turnGunTo(this.robot.scannedAngle);
            this.robot.fire(2);
            this.robot.fire(2);
            //this.robot.turnGunTo(angulo);
        }
        if(onAWall){
            this.robot.fire(1.5);
        }

    }

    @Override
    public void onHitByBullet() {
        cantDisparosRecibidos++;
        this.robot.turnGunTo(this.robot.hitByBulletAngle);
        this.robot.fire(2);
        if( (cantDisparosRecibidos % 3)==0) {
            this.robot.turnRight(90);
            this.robot.ahead(50);
            onAWall = false;
        } // cuando me pegan 3 veces me muevo.
    }

    @Override
    public void onHitWall() {
        cantToques++;
        if(!onAWall){onAWall = true;}
        if(cantToques > 1){
            sincronizarArma = true;
        }
        volver=!volver;


        // Si puedo saber donde hay una esquina, es ir en la contraria

        // Si no se puede saber, es girar a la derecha o izquierda con una chance
    }
}
