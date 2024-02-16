import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PuertaControl implements Runnable {

    private int id;
    private static Lock controlEntrar = new ReentrantLock();
    private static Condition condicionControlEntrar = controlEntrar.newCondition();
    private static Lock controlSalir = new ReentrantLock();
    private static Condition condicionControlSalir = controlSalir.newCondition();
    private ArrayList<Integer> puerta;
    private Random random = new Random();



    public PuertaControl(int id, ArrayList<Integer> puerta) {
        this.id = id;
        this.puerta = puerta;
    }

    public void run() {
        //obtiene el cerrojo del control de entrada(Prodcutor)
        controlEntrar.lock();
        try {
            //espera mientras no haya un control vacío
            while(!puerta.contains(Integer.valueOf(-1))) { //mientras no haya un control vacío
                condicionControlEntrar.await();
            }
            //entra en el control
            int i = puerta.indexOf(Integer.valueOf(-1));
            puerta.set(i, id);
            System.out.println("Entra en la puerta el visitante "+id+" contenido puerta: "+puerta);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            condicionControlEntrar.signalAll();
            //Despierta a todos los hilos que estén esperando para intentar entrar en el control
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //libera el cerrojo del control de entrada
            controlEntrar.unlock();
        }
        //obtiene el cerrojo del control de salida(Cosumidor)
        controlSalir.lock();
        try {
            //solo sale si esta en el control
            while(!puerta.contains(Integer.valueOf(id))) { //mientras no esté en el control
                condicionControlSalir.await();
            }
            //sale del control
            int i = puerta.indexOf(Integer.valueOf(id));
            puerta.set(i, -1);
            System.out.println("Sale de la puerta el visitante "+id+" contenido puerta: "+puerta);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); //tiempo pasando
            condicionControlSalir.signalAll();
            //Avisa a todos los hilos que estén esperando para intentar salir del control
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //libera el cerrojo del control de salida
            controlSalir.unlock();
        }
        //Creo un numero aleatorio y si es par, se monta en la montaña rusa, si es impar, se monta en la noria
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




