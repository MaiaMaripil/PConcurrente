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
public class Parque {

    public static void main(String[] arg) {
        ParqueAcuatico r = new ParqueAcuatico(5);
        Thread e = new Thread(new Encargado("ENCARGADO", r));
        e.start();

        for (int i = 0; i < 6; i++) {
            try {
                Thread t = new Thread(new Visitante("VISITANTE " + (i + 1), r));
                t.start();
                Thread.sleep(3000);
                
            }catch (InterruptedException s) {
                s.printStackTrace();
            }

        }
    }
}

class Visitante implements Runnable {

    String nombre;
    ParqueAcuatico rC;

    public Visitante(String n, ParqueAcuatico r) {
        this.nombre = n;
        this.rC = r;
    }

    public void run() {
        try {
            int t;
            rC.subirEscalera(nombre);
            t = rC.usarTobogan(nombre);
            Thread.sleep(5000);
            rC.terminar(nombre, t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Encargado implements Runnable {

    String nombre;
    ParqueAcuatico rC;

    public Encargado(String n, ParqueAcuatico r) {
        this.nombre = n;
        this.rC = r;
    }

    public void run() {
        try {
            int t;
            while (true) {
                t = (int) (Math.random() * 2) + 1;

                rC.habilitarVisitante(nombre, t);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}





class ParqueAcuatico {

    Semaphore[] escaleras;
    Semaphore mutex, toboganA, toboganB, encargado, usarTobogan;

    public ParqueAcuatico(int n) {
        this.escaleras = new Semaphore[n];
        for (int i = 0; i < this.escaleras.length; i++) {
            escaleras[i] = new Semaphore(1); //inician todos los escalones habilitados
        }
        this.toboganA = new Semaphore(1);
        this.toboganB = new Semaphore(1);
        this.mutex=new Semaphore(1);
        this.encargado=new Semaphore(0);
        this.usarTobogan=new Semaphore(0);
    }

    public void subirEscalera(String n) throws InterruptedException {
        for (int i = 0; i < this.escaleras.length; i++) {
            escaleras[i].acquire();
            System.out.println(n + " EN EL ESCALON " + (i + 1));
            if (i > 0) {
                escaleras[i - 1].release();
            }
        }
        escaleras[escaleras.length-1].release(); //libera el ultimo escalon
    }

    public int usarTobogan(String n) throws InterruptedException {
        int t = 0;
        System.out.println(n + " ESPERANDO A USAR TOBOGAN");
        mutex.acquire();
        encargado.release();//avisa al encargado 
        mutex.release();
        usarTobogan.acquire();
        
        if (toboganA.tryAcquire()) {
            t = 1;
            System.out.println(n + " BAJANDO POR TOBOGAN 1");
        } else {toboganB.acquire(); 
            t = 2;
            System.out.println(n + " BAJANDO POR TOBOGAN 2");
        }
        return t;
    }

    public void terminar(String n, int t) {
        System.out.println(n + " TERMINO DE USAR TOBOGAN " + t);
        if (t == 1) {
            toboganA.release();

        } else if (t == 2) {
            toboganB.release();
        }
    }

    public void habilitarVisitante(String n, int t) throws InterruptedException {
        System.out.println(n + " ESPERANDO A VISITANTE");
        encargado.acquire();
      
        if (t == 1) {
            toboganA.acquire();//adquiere primero el permiso
            toboganA.release();//le da el permiso
        } else if (t == 2) {
            toboganB.acquire();
            toboganB.release();
        }
          usarTobogan.release(); //lo habilita
    }
}
