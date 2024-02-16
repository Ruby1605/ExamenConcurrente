import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Noria implements Runnable {

    private static int AFORO_NORIA = 8; //aforo de la noria

    private int id;
    private static Semaphore aforo = new Semaphore(AFORO_NORIA);
    private static ArrayList<Integer> noria = new ArrayList<>();


    public Noria(int id) {
        this.id = id;
    }

    public void run() {
        //el visitante intenta entrar en la noria adquiriendo el semaforo
        try {
            aforo.acquire();
            noria.add(id);//entrar en la noria
            System.out.println("Entra en la noria el visitante: "+id+" Noria: "+noria);
            System.out.println(id+": Qué alto!!");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            System.out.println("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //el visitante sale de la noria liberando el semaforo
            int id = noria.remove(0);
            System.out.println("El visitante "+id+" sale de la noria");
            aforo.release();
        }
    }
}
