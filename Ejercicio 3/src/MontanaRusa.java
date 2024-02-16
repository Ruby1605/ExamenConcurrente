import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MontanaRusa implements Runnable {

    private static int AFORO_RUSA = 6; //aforo de la noria

    private int id;
    private static ArrayList<Integer> montana = new ArrayList<>();

    private static Lock controlEntrarMontana = new ReentrantLock();
    private static Condition condicionControlEntrarMontana = controlEntrarMontana.newCondition();


    public MontanaRusa(int id) {
        this.id = id;
    }

    public void run() {
        //Coge el cerrojo del monitor
        controlEntrarMontana.lock();
        try {
            //espera mientras no hay sitio en la montaña rusa
            while(montana.size()>=AFORO_RUSA) {
                condicionControlEntrarMontana.await();
            }
            //entra en la montaña rusa
            montana.add(id);
            System.out.println("Entra en la montaña rusa el visitante: "+id+" Montaña rusa: "+montana);
            System.out.println(id+": Qué mareo!!");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            System.out.println("");
            montana.remove(Integer.valueOf(id));
            //Sale de la montaña rusa
            System.out.println("Sale de la montaña rusa el visitante: "+id+" Montaña rusa: "+montana);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            System.out.println("");
            //Despierta a todos los hilos que estén esperando para entrar en la montaña rusa
            condicionControlEntrarMontana.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //Libera el cerrojo del monitor
            controlEntrarMontana.unlock();
        }


    }
}
