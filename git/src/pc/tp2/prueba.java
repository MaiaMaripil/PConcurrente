/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

/**
 *
 * @author famil
 */
public class prueba {

    public static void main(String[] args) {
        ThreadEjemplo t1 = new ThreadEjemplo("Maria Jose");
        ThreadEjemplo t2 = new ThreadEjemplo("Jose Maria");
        Thread h1 = new Thread(t1);
        Thread h2 = new Thread(t2);
        h1.start();
        h2.start();
        System.out.println("Termina thread main");
    }
}

class ThreadEjemplo implements Runnable {

    String nombre;

    public ThreadEjemplo(String n) {
        this.nombre = n;
    }

    public String getName() {
        return this.nombre;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " " + getName());
        }
        System.out.println("Termina thread " + getName());
    }
}
