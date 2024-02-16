import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
        private static int NCONTROL = 4; //4 controles en la puerta
        private static int NVISITANTES = 30; //30 visitantes



        public static void main(String[] args) {
            ArrayList<Integer> puerta = new ArrayList<>();

            for(int i=0;i<NCONTROL;i++) puerta.add(-1);
            Thread hilos[] = new Thread[NVISITANTES];
            for (int i = 0; i < NVISITANTES; i++) {
                hilos[i] = new Thread(new PuertaControl(i, puerta));
                hilos[i].start();
            }
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