import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PuertaControl implements Runnable {

    private int id;
    private Semaphore control;
    private ArrayList<Integer> puerta;
    private Random random = new Random();

    public static int VACIO = -1; //Elementos vacio es decir esta libre

    public PuertaControl(int id, Semaphore control, ArrayList<Integer> puerta) {
        this.id = id;
        this.control = control;
        this.puerta = puerta;
    }

    public void run() {
        try {
            //el visitante intenta entrar en la puerta adquiriendo el semaforo
            control.acquire();
            int i = puerta.indexOf(Integer.valueOf(VACIO));//entrar en la puerta
            puerta.set(i, id);
            System.out.println("Entra en la puerta el visitante "+id+" contenido puerta: "+puerta);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //el visitante sale de la puerta liberando el semaforo y entra al parque
            int i = puerta.indexOf(Integer.valueOf(id));
            puerta.set(i, VACIO);
            //parque.add(id);
            System.out.println("El visitante "+id+" sale de la puerta y entra al parque");
            control.release();
            //el visitante elige una atraccion
            if(random.nextInt()%2==0) {
                Thread mr = new Thread(new MontanaRusa(id));
                mr.start();
                try {
                    mr.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PuertaControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Thread nor = new Thread(new Noria(id));
                nor.start();
                try {
                    nor.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PuertaControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}