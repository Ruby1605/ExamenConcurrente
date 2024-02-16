import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class MontanaRusa implements Runnable {

    private static int AFORO_RUSA = 6; //Aforo de la montaña rusa

    private int id;
    private static Semaphore aforo = new Semaphore(AFORO_RUSA);
    private static ArrayList<Integer> montana = new ArrayList<>();


    public MontanaRusa(int id) {
        this.id = id;
    }

    public void run() {
        //el visitante intenta entrar en la montaña rusa adquiriendo el semaforo
        try {
            aforo.acquire();
            montana.add(id);//entrar en la montaña rusa
            System.out.println("Entra en la montaña rusa el visitante: "+id+" Montaña: "+montana);
            System.out.println(id+": Yuhuuuu!!");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            System.out.println("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //el visitante sale de la montaña rusa liberando el semaforo
            int id = montana.remove(0);
            System.out.println("El visitante "+id+" sale de la montaña rusa");
            aforo.release();
        }
    }
}
