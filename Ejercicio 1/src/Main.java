import java.util.Random;

public class Main {
    public static int LANZAMIENTOS = 100;
    public static int NDADOS = 5;

    public static void main(String[] args) {
        lanzamiento(false);//Lanzamiento sin condiciones aleatorias
        lanzamiento(true);//Lanzamiento con condiciones aleatorias
    }
    private static void lanzamiento(boolean condiciones){
        Random random = new Random();
        if (condiciones){
        System.out.printf("Lanzamiento con condiciones aleatorias");
        }else{
            System.out.printf("Lanzamiento sin condiciones aleatorias");
        }
        Dado dados [] = new Dado[NDADOS];//5 dados
        Thread hilos [] = new Thread[NDADOS];//5 hilos
        int resultado[] = new int[6];//veces que sale cada numero del dado
        double porcentaje[] = new double[6];//porcentaje de veces que sale cada numero del dado
        int viento = random.nextInt(100)+1 ;
        int superficie = random.nextInt(100)+1 ;

        if (!condiciones){
            viento = 0;
            superficie = 0;
        }
        //100 lanzamientos
        for (int lanz = 1; lanz <= LANZAMIENTOS; lanz++) {
            System.out.println("\nLanzamiento " + lanz);
            for (int i = 0; i < NDADOS; i++) {
                dados[i] = new Dado(i, viento, superficie);
                hilos[i] = new Thread(dados[i]);
                hilos[i].start();
            }
            for (int i = 0; i < NDADOS; i++) {
                try {
                    hilos[i].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < NDADOS; i++) {
                resultado[dados[i].getValor() - 1]++;
            }
        }
        //Calculo de frecuencia de numeros
        System.out.println("--------------------Frecuencia de numeros--------------------");
        for (int i = 0; i < 6; i++) {
            System.out.println("Numero " + (i + 1) + " : " + resultado[i]);
        }
        //Calculo de porcentaje de numeros
        System.out.println("--------------------Porcentaje de numeros--------------------");
        for (int i = 0; i < 6; i++) {
            porcentaje[i] = (resultado[i] * 100) / (LANZAMIENTOS*5);
            System.out.println("Numero " + (i + 1) + " : " + porcentaje[i] + "%");
        }
    }
}