package Rip912;

import robocode.JuniorRobot;


public class futbolChampagne implements Estrategia{

    private JuniorRobot robot;
    private boolean stopWhenISeeARobot = false;
    private boolean volver =false;
    private boolean volverArma =false;
    private boolean onAWall = false;
    private MapToWall myMap;

    public futbolChampagne(JuniorRobot robot) {
        this.robot = robot;
    }

    public void run(){
        // Que hago en cada turno que sea parte de la estrategia
        // Moverse hacia una pared
            // Elegir una pared
            // Si veo a un robot no me importa, estoy buscando una pared
        stopWhenISeeARobot = false;
        myMap = chooseAWall();
            // Ahora si
        stopWhenISeeARobot = true;
            // Moverse hacia ella
        robot.ahead(myMap.getDistanceFromWall());
        robot.turnRight(90);
        robot.turnGunRight(90);

        while(onAWall){
            if(!volver){
                robot.ahead(20);
            }else{
                robot.back(20);
            }
            if(robot.gunHeading == 90 ){
                volverArma = true;
            } else if (robot.gunHeading == 270) {
                volverArma = false;
            }
            if(volverArma){
                robot.turnGunLeft(20);
            }else{
                robot.turnGunRight(20);
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
        // Estoy de camino hacia la pared
        if (!onAWall) {
            // parar
            int x = 1;
                

            // ver si es util disparar
            // seguir
            // return
        }


    }

    @Override
    public void onHitByBullet() {

    }

    @Override
    public void onHitWall() {
        if(!onAWall){onAWall = true;}
        volver=!volver;
        // Si puedo saber donde hay una esquina, es ir en la contraria

        // Si no se puede saber, es girar a la derecha o izquierda con una chance
    }
}
