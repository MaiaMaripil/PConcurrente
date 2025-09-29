/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp4;

import java.util.concurrent.Semaphore;

/**
 *
 * @author famil
 */
public class Punto3 {

    public static void main(String[] arg) {
        Ejecucion e = new Ejecucion();
        Hilos h1 = new Hilos("P1", e);
        Hilos h2 = new Hilos("P2", e);
        Hilos h3 = new Hilos("P3", e);
        Thread t1 = new Thread(h1, h1.getNombre());
        Thread t2 = new Thread(h2, h2.getNombre());
        Thread t3 = new Thread(h3, h3.getNombre());

        t1.start();
        t2.start();
        t3.start();
    }
}

class Hilos implements Runnable {

    String nombre;
    Ejecucion rC;

    public Hilos(String n, Ejecucion r) {
        this.nombre = n;
        this.rC = r;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void run() {
        try {
            while (true) {

                rC.ejecutar();
            }
        } catch (InterruptedException e) {
            System.out.println("Error");

        }
    }
}

class Ejecucion {

    Semaphore semP1 = new Semaphore(1); //el semaforo de P1 esta habilitado desde un inicio
    Semaphore semP2 = new Semaphore(0);
    Semaphore semP3 = new Semaphore(0);

    public void ejecutarP1() throws InterruptedException {

        semP1.acquire();
        System.out.println("Ejecutando "+Thread.currentThread().getName());
        Thread.sleep(100);
        semP3.release();

    }

    public void ejecutarP2() throws InterruptedException {
        semP2.acquire();
        System.out.println("Ejecutando "+Thread.currentThread().getName());
        Thread.sleep(100);
        semP1.release();
    }

    public void ejecutarP3() throws InterruptedException {
        semP3.acquire();
        System.out.println("Ejecutando "+Thread.currentThread().getName());
        Thread.sleep(100);
        semP2.release();
    }

    public void ejecutar() throws InterruptedException {
        switch (Thread.currentThread().getName()) {
            case "P1":
                ejecutarP1();
                break;
            case "P2":
                ejecutarP2();
                break;
            case "P3":
                ejecutarP3();
                break;
        }
    }

}
