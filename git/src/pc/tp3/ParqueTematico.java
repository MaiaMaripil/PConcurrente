/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp3;

import java.util.Random;

/**
 *
 * @author famil
 */
public class ParqueTematico {

    public static void main(String[] args) {
        Parque parque = new Parque();
        Thread[] hilos = new Thread[10];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(parque, "Visitante " + (i + 1));
            hilos[i].start();

        }
        /* Area[] areas = new Area[3];
        areas[0] = new Area("a1", 5);
        areas[1] = new Area("a2", 3);
        areas[2] = new Area("a3", 2);

        Visitante v1 = new Visitante(areas[0], "v1");
        Visitante v2 = new Visitante(areas[2], "v2");
        Visitante v3 = new Visitante(areas[2], "v3");

        Thread t1 = new Thread(v1, "v1");
        Thread t2 = new Thread(v2, "v2");
        Thread t3 = new Thread(v3, "v3");

        t1.start();
        t2.start();
        t3.start();*/

    }

}

class Area {

    int espacio;
    String nombre;

    public Area(int e, String n) {
        this.espacio = e;
        this.nombre = n;
    }

    public synchronized boolean disponible() {
        boolean disp = false;
        if (espacio > 0) {
            disp = true;
        }
        return disp;
    }

    public synchronized void hacerReserva() {
        this.espacio--;

    }

    public String getNombre() {
        return this.nombre;
    }
}

class Parque implements Runnable {

    Area[] areas = new Area[5]; //recurso compartido

    public Parque() {
        //inicializa cada area con 2 espacios disponibles
        for (int i = 0; i < areas.length; i++) {
            areas[i] = new Area(2, "Area " + (i + 1));
        }
    }

    public void reservar(Area a) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " dese reservar un lugar en el area " + a.getNombre());
        if (a.disponible()) {
            System.out.println("Haciendo reserva de " + Thread.currentThread().getName());
            a.hacerReserva();
            Thread.sleep(100);
            System.out.println("La reserva de " + Thread.currentThread().getName() + " pudo realizarce con exito!");

        } else {
            System.out.println("La reserva de " + Thread.currentThread().getName() + " No pudo realizarse ya que no hay lugar disponible.");
        }
    }

    public void run() {
        try {
            Random rand = new Random();
            int area = rand.nextInt(areas.length);
            reservar(areas[area]);
        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }
    }
}


/*
class Area {

    private int espacioDisp;
    private String nombre;

    public Area(String n, int d) {
        this.nombre = n;
        this.espacioDisp = d;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int espacioDisp() {
        return this.espacioDisp;
    }

    public void setEspacioDisp() {
        espacioDisp = espacioDisp--;
    }

    public synchronized boolean reservar(String n) {
        boolean exito = false;
        if (espacioDisp > 0) {
            System.out.println(n + " realizo con exito su reserva en el area " + nombre);
            exito = true;
        } else {
            System.out.println(n + " no pudo realizar su reserva por no haber disponibilidad en el area " + nombre);

        }
        return exito;
    }
}

class Visitante implements Runnable {

    private Area area;
    private String nombre;

    public Visitante(Area a, String n) {
        this.area = a;
        this.nombre = n;
    }

    public void run() {
        try {
            area.reservar(nombre);
        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }

    }
}*/
