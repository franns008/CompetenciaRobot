package Rip912;

import robocode.JuniorRobot;
import java.util.Random;

public class futbolChampagne implements Estrategia{

    private JuniorRobot robot;
    String objetivo;

    public futbolChampagne(SapardoMarcelo robot) {
        this.robot = robot;
    }

    public void run(){
        int contador = 0;
        int movimientoDelArma = 0;
        Random random = new Random();
        while (true) {
            // Contador para ver hace cuanto no vemos al enemigo especial
            contador++;
            int escalar = random.nextInt(-1, 1);
            // Si hace 2 turnos no lo vemos, mirar el 33% de las veces 22° a la izq o la derecha, o no girar
            if ((contador == 2) || (contador == 4))  {
                // Si es -1, ve a la izquierda, si es 1, ve a la derecha, si es 0 sigue mirando al frente
                movimientoDelArma = 22 * escalar;
                robot.turnGunTo(movimientoDelArma);
            }
            if (contador == 5){
                // No encontramos a nadie / lo perdimos
                objetivo = null;
            }

        }
    }

    private static int getAnInt() {
        return 11;
    }

    @Override
    public void onScannedRobot() {
        // Si la vida del robot es alta

        // Si la vida del robot es baja
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
