import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Noria implements Runnable {

    private static int AFORO_NORIA = 8; //aforo de la noria

    private int id;
    private static ArrayList<Integer> noria = new ArrayList<>();

    private static Lock controlEntrarNoria = new ReentrantLock();
    private static Condition condicionControlEntrarNoria = controlEntrarNoria.newCondition();


    public Noria(int id) {
        this.id = id;
    }

    public void run() {
        //Coge el cerrojo del monitor
        controlEntrarNoria.lock();
        try {
            //espera mientras no hay sitio en la noria
            while(noria.size()>=AFORO_NORIA) {
                condicionControlEntrarNoria.await();
            }
            //entra en la noria
            noria.add(id);
            System.out.println("Entra en la noria el visitante: "+id+" Noria: "+noria);
            System.out.println(id+": Qué alto!!");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            System.out.println("");
            noria.remove(Integer.valueOf(id));
            //Sale de la noria
            System.out.println("Sale de la noria el visitante: "+id+" Noria: "+noria);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            System.out.println("");
            //Despierta a todos los hilos que estén esperando para entrar en la montaña rusa
            condicionControlEntrarNoria.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //Libera el cerrojo del monitor
            controlEntrarNoria.unlock();
        }


    }
}

