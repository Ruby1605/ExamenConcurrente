import java.util.Random;

public class Dado implements Runnable{

    private int valor;
    private int viento;
    private int superficie;
    private  Random  random=  new  Random();
    private int  numero;

    private int valorviento;
    private int valorSuperficie;

    public Dado(int numero, int viento, int superficie){
        this.numero = numero;
        this.viento = viento;
        this.superficie = superficie;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public void run() {
        //simulacion de lanzamiento de dado (10 valores aleatorios entre 1 y 6
        for (int i = 0; i < 10; i++) {
            valor = random.nextInt(6) + 1;
            if (viento >0 ){//si hay viento se suma un valor aleatorio entre 1 y el valor de viento
                valorviento = random.nextInt(viento) + 1;

                valor = valor + valorviento;
                valor = (valor%6)+1;
            }
            if (superficie >0){//si hay superficie se suma un valor aleatorio entre 1 y el valor de superficie
                valorSuperficie = random.nextInt(superficie) + 1;
                valor = valor + valorSuperficie;
                valor = (valor%6)+1;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Se muestra ek valor del dado en el lanzamiento
        System.out.println("Valor del dado " + numero +":"+ valor);
    }
}
