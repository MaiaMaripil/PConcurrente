/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

/**
 *
 * @author famil
 */
public class Corredor {

    public static void main(String[] args) {
        CorredorRun[] array = new CorredorRun[5];
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < array.length; i++) {
            array[i] = new CorredorRun("corredor " + (i + 1));
            hilos[i] = new Thread(array[i]);
            hilos[i].start();
        }

        try {
            for (int i = 0; i < array.length; i++) {
                hilos[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR EN EL MAIN");
        }

        System.out.println("El corredor con mayor distancia recorrida es:  " + mayorDistancia(array).getNombre());
        for (int i = 0; i < array.length; i++) {
            System.out.println("CORREDOR: " + (i + 1) + "| DISTANCIA RECORRIDA: " + array[i].getDistancia());
        }
    }

    public static CorredorRun mayorDistancia(CorredorRun[] array) {
        CorredorRun corredor = array[0];
        CorredorRun aux;

        for (int i = 0; i < array.length; i++) {
            aux = array[i];
            if (corredor.getDistancia() < aux.getDistancia()) {
                corredor = aux;
            }
        }
        return corredor;
    }
}

class CorredorRun implements Runnable {

    String nombre;
    double distRecorrida = 0;

    public CorredorRun(String n) {
        this.nombre = n;
    }
    
    public double getDistancia(){
        return this.distRecorrida;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void run() {
        //simula los 100 pasos
        for (int i = 0; i <= 100; i++) {
            int aux = (int) (Math.random() * 10) + 1;
            System.out.println("Soy el corredor: " + nombre + " En el paso numero: " + i + " recorri: " + aux + " km.");
            distRecorrida = distRecorrida + aux;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("ERROR EN EJECUCION");
            }
        }
        System.out.println("TERMINA EL RECORRIDO DE " + nombre);
    }
}
