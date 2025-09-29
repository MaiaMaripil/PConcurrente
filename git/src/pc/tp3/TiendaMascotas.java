/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp3;

/**
 *
 * @author famil
 */
public class TiendaMascotas {
//deberia ser una clase Hamster runnable? o sigo el ejemplo del banco del tp.

    public static void main(String[] args) {
        Jaula unaJaula = new Jaula();
        Hamster h1 = new Hamster("Hamster 1",unaJaula);
        Hamster h2 = new Hamster("Hamster 2",unaJaula);
        Hamster h3 = new Hamster("Hamster 3",unaJaula);
        
        Thread t1 = new Thread(h1, "Hamster 1");
        Thread t2 = new Thread(h2, "Hamster 2");
        Thread t3 = new Thread(h3, "Hamster 3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class Hamster implements Runnable {

    String nombre;
    Jaula rC;
    boolean comio = false;
    boolean corrio = false;
    boolean durmio = false;

    public Hamster(String n, Jaula r) {
        this.nombre = n;
        this.rC = r;
    }

    @Override
    public void run() {
        try {
            while (!(comio && corrio && durmio)) {

                if (!comio && rC.comer(nombre)) {
                    Thread.sleep(500);
                    rC.terminarComer(nombre);
                    comio = true;
                }
                if (!corrio && rC.correr(nombre)) {
                    Thread.sleep(500);
                    rC.terminarCorrer(nombre);
                    corrio = true;
                }
                if (!durmio && rC.descansar(nombre)) {
                    Thread.sleep(500);
                    rC.terminarDescansar(nombre);
                    durmio = true;
                }
                
            }

        } catch (InterruptedException e) {
            System.out.println("ERROR");

        }
    }
}

class Jaula {

    boolean plato = true;
    boolean hamaca = true;
    boolean rueda = true;
//recurso compartido

    public synchronized boolean comer(String n) {
        boolean pudoComer = false;
        if (plato) {
            plato = false;
            pudoComer = true;
            System.out.println(n + " esta comiendo...");
        }
        return pudoComer;
    }

    public synchronized void terminarComer(String n) {
        System.out.println(n + " TERMINO DE COMER");
        plato = true;
    }

    public synchronized boolean correr(String n) {
        boolean pudoCorrer = false;
        if (rueda) {
            pudoCorrer = true;
            rueda = false;
            System.out.println(n + " esta corriendo...");
        }
        return pudoCorrer;
    }

    public synchronized void terminarCorrer(String n) {
        System.out.println(n + " TERMINO DE CORRER");
        rueda = true;
    }

    public synchronized boolean descansar(String n) {
        boolean pudoDormir = false;
        if (hamaca) {
            hamaca = false;
            pudoDormir = true;
            System.out.println(n + " esta descansando...");
        }
        return pudoDormir;
    }

    public synchronized void terminarDescansar(String n) {
        System.out.println(n + " TERMINO DE USAR HAMACA");
        hamaca = true;
    }

}

/*
class Actividades implements Runnable {

    Jaula recurso = new Jaula();

    private void comer(String n) throws InterruptedException {
        System.out.println(n + " quiere COMER.");
        recurso.comer(n);
    }

    private void correr(String n) throws InterruptedException {
        System.out.println(n + " quiere CORRER.");
        recurso.correr(n);
    }

    private void descansar(String n) throws InterruptedException {
        System.out.println(n + " quiere DESCANSAR.");
        recurso.descansar(n);
    }

    public void run() {
        try {
            comer(Thread.currentThread().getName());
            correr(Thread.currentThread().getName());
            descansar(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }
    }
}
*/
/*

class Hamster implements Runnable {

    private Actividades act;

    public Hamster(Actividades act) {
        this.act = act;
    }

    public void run() {
        try {
            act.comerDelPlato();
        } catch (InterruptedException e) {
            System.out.println("No pudo comer");
        }
        try {
            act.correrRueda();
        } catch (InterruptedException e) {
            System.out.println("No pudo correr en la rueda");
        }
        try {
            act.usarAmaca();
        } catch (InterruptedException e) {
            System.out.println("No pudo dormir");
        }
    }

}

class Actividades {

    private int amaca, rueda, plato;

    public Actividades() {
        amaca = 0;
        rueda = 0;
        plato = 0;
    }

    public synchronized void correrRueda() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        rueda += 1;
        Thread.sleep(1000);
        //System.out.println("Corriendo:"+Thread.currentThread().getName());
    }

    public synchronized void usarAmaca() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        amaca += 1;
        Thread.sleep(1000);
        //System.out.println("Descanzando:"+Thread.currentThread().getName());
    }

    public synchronized void comerDelPlato() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        plato += 1;
        Thread.sleep(1000);
        //System.out.println("Comiendo:"+Thread.currentThread().getName());
    }

    public synchronized String toString() {
        return "Amaca: " + amaca + " Rueda: " + rueda + " Plato: " + plato;
    }
}
 */
