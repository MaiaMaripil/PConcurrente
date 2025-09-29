/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp4;

import java.util.concurrent.Semaphore;

/**
 *
 * @author maiamaripil
 */
public class CentroCopiado {

    public static void main(String[] arg) {
        Impresoras rec = new Impresoras();

        for (int i = 0; i < 10; i++) {

            Thread t1 = new Thread(new Cliente("Cliente " + (i + 1), rec));
            t1.start();
        }

    }
}

class Cliente implements Runnable {

    String nombre;
    Impresoras rC;

    public Cliente(String n, Impresoras rec) {
        this.nombre = n;
        this.rC = rec;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void run() {
        try {
            int rta = -1;
            while (rta < 0) {
                rta = rC.imprimir(nombre); //intenta imprimir
                if (rta > -1) {
                    Thread.sleep(200);
                    rC.terminar(rta, nombre);

                } else {
                    //espera antes de volver a intentar
                    Thread.sleep(1000);
                }
            }

        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }

    }

}

class Impresoras {

    Semaphore[] impresoras = new Semaphore[3];
    Semaphore mutex = new Semaphore(1);

    public Impresoras() {
        for (int i = 0; i < impresoras.length; i++) {
            impresoras[i] = new Semaphore(1); //todas las impresoras disponibles
        }
    }

    public int imprimir(String n) throws InterruptedException {
        boolean exito = false;
        int i = 0;
        mutex.acquire(); //seccion critica
        try {
            while (!exito && i < impresoras.length) {
                if (impresoras[i].tryAcquire()) {
                    exito = true;
                    System.out.println(n + " Imprimiendo en impresora " + i + "...");
                } else {
                    i++;
                }
            }
            if (!exito) {
                i = -1;
                System.out.println(n + " NO PUDO IMPRIMIR, ESPERA Y VUELVE A INTENTAR...");
            }
        } finally {
            mutex.release(); //otro hilo puede seguir
        }
        return i;
    }

    public void terminar(int num, String n) {
        System.out.println(n + " TERMINO DE IMPRIMIR EN IMPRESORA " + num);
        impresoras[num].release();
    }
}
