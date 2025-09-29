/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp4;

/**
 *
 * @author maiamaripil
 */
public class EjParcial {

    public static void main(String[] arg) {
        Pizarra r = new Pizarra();

        for (int i = 0; i < 6; i++) {

            Thread t = new Thread(new Hilo("HILO " + (i + 1), r));
            t.start();

        }
    }
}

class Hilo implements Runnable {

    String nombre;
    Pizarra rC;

    public Hilo(String n, Pizarra r) {
        this.nombre = n;
        this.rC = r;
    }

    public void run() {
        try {
            while (true) {
                if (!rC.getOcupada()) {
                    rC.escribirPizarra(nombre);
                    Thread.sleep(2000);
                    rC.borrarPizarra(nombre);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }
}

class Pizarra {

    boolean ocupada = false;

    public synchronized void escribirPizarra(String n) {
        System.out.println(n + " escribiendo en pizarra...");
        this.ocupada = true;
    }

    public synchronized void borrarPizarra(String n) {
        System.out.println(n + " borrando pizarra...");
        this.ocupada = false;
    }

    public synchronized boolean getOcupada() {
        return ocupada;
    }

}
