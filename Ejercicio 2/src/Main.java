import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static int NCONTROL = 4; //4 controles en la puerta
    private static int NVISITANTES = 30; //30 visitantes

    public static int VACIO = -1; //Elementos vacio es decir esta libre


    public static void main(String args[]) {
        Semaphore control = new Semaphore(NCONTROL);
        ArrayList<Integer> puerta = new ArrayList<>();
        //Inicializamos la puerta con elementos vacios
        for(int i=0;i<NCONTROL;i++) puerta.add(VACIO);
        Thread hilos[] = new Thread[NVISITANTES];
        //Creamos los hilos
        for (int i = 0; i < NVISITANTES; i++) {
            hilos[i] = new Thread(new PuertaControl(i, control, puerta));
            hilos[i].start();
        }
        //Esperamos a que terminen los hilos
        for(int i=0;i<NVISITANTES;i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Fin del programa");

    }
}