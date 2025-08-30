package Rip912;

import robocode.JuniorRobot;

public class futbolChampagne implements Estrategia{

    private JuniorRobot robot;
    private boolean stopWhenISeeARobot = false;
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
        while (myMap.getDistanceFromWall() != 0) {
            // Esto se hace ya que puedo ver a un robot en el camino
            robot.ahead(myMap.getDistanceFromWall());
        }
        robot.turnTo(180);
        onAWall = true;

    }

    private MapToWall chooseAWall() {
        // 0 = Pared arriba, 1 = Pared derecha, 2 = Pared abajo, 3 = Pared izq
        double[] arrayOfDistances = {
                robot.fieldHeight - robot.robotY,
                robot.fieldWidth - robot.robotX,
                robot.robotY,
                robot.robotX
        };
        int indexMin = Integer.MAX_VALUE;
        for (int i=0; i < arrayOfDistances.length; i++){
            if (arrayOfDistances[i] < indexMin){
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
        // Correr hacia algun lado

        // Calcular por donde estaría

        // Disparar
    }

    @Override
    public void onHitWall() {
        // Si puedo saber donde hay una esquina, es ir en la contraria

        // Si no se puede saber, es girar a la derecha o izquierda con una chance
    }
}
