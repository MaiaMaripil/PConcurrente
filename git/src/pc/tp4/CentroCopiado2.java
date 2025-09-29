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
public class CentroCopiado2 {

    public static void main(String[] args) {
        Impresoras rec = new Impresoras();
        Cliente[] clientes = new Cliente[10];

        for (int i = 0; i < clientes.length; i++) {
            int tipo = (int) (Math.random() * 3) + 1;
            Thread t1 = new Thread(clientes[i] = new Cliente("Cliente " + (i + 1), rec, tipo));
            t1.start();
        }
    }
}

class Cliente implements Runnable {

    String nombre;
    Impresoras rC;
    int tipo;

    public Cliente(String n, Impresoras rec, int t) {
        this.nombre = n;
        this.rC = rec;
        this.tipo = t;
    }

    public String getNombre() {
        return this.nombre + " TIPO: " + this.tipo;
    }

    public void tipoA() throws InterruptedException {
        int rta;
        do {
            rta = rC.imprimirA(nombre);
            Thread.sleep(200);
            if (rta >= 0) {
                rC.terminarA(rta, nombre);
            } else {
                Thread.sleep(1000);
            }
        } while (rta < 0);
    }

    public void tipoB() throws InterruptedException {
        int rta;
        do {
            rta = rC.imprimirB(nombre);
            Thread.sleep(200);
            if (rta >= 0) {
                rC.terminarB(rta, nombre);
            } else {
                Thread.sleep(1000);
            }
        } while (rta < 0);
    }

    public void tipoC() throws InterruptedException {
        int[] rta = new int[2];
        do {
            rta = rC.imprimirC(getNombre());
            Thread.sleep(200);
            if (rta[0] >= 0) {
                rC.terminarC(rta, getNombre());
            } else {
                Thread.sleep(1000);
            }
        } while (rta[0] < 0);
    }

    @Override
    public void run() {
        int t;
        try {
            switch (this.tipo) {
                case 1:
                    tipoA();
                    break;
                case 2:
                    tipoB();
                    break;
                case 3:
                    tipoC();
                    break;
            }

        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }
    }
}

class Impresoras {
//por ahora solo semaforos binarios

    Semaphore[] tipoA = new Semaphore[2];
    Semaphore[] tipoB = new Semaphore[2];
    Semaphore mutexA = new Semaphore(1);
    Semaphore mutexB = new Semaphore(1);

    public Impresoras() {
        for (int i = 0; i < tipoA.length; i++) {
            tipoA[i] = new Semaphore(1); //todas las impresoras A disponibles
        }
        for (int i = 0; i < tipoB.length; i++) {
            tipoB[i] = new Semaphore(1); //todas las impresoras B disponibles
        }
    }

    public int imprimirA(String n) throws InterruptedException {
        boolean exito = false;
        int i = 0;
        mutexA.acquire(); //seccion critica
        try {
            while (!exito && i < tipoA.length) {
                if (tipoA[i].tryAcquire()) {
                    exito = true;
                    System.out.println(n + " Imprimiendo en impresora A" + i + "...");
                } else {
                    i++;
                }
            }
            if (!exito) {
                i = -1;
                System.out.println(n + " NO PUDO IMPRIMIR en A, ESPERA Y VUELVE A INTENTAR...");
            }
        } finally {
            mutexA.release(); //otro hilo puede seguir
        }
        return i;
    }

    public void terminarA(int num, String n) {
        System.out.println(n + " Termino de imprimir en impresora A " + num);
        tipoA[num].release();
    }

    public int imprimirB(String n) throws InterruptedException {
        boolean exito = false;
        int i = 0;
        mutexB.acquire(); //seccion critica
        try {
            while (!exito && i < tipoB.length) {
                if (tipoB[i].tryAcquire()) {
                    exito = true;
                    System.out.println(n + " Imprimiendo en impresora B" + i + "...");
                } else {
                    i++;
                }
            }
            if (!exito) {
                i = -1;
                System.out.println(n + " NO PUDO IMPRIMIR en B, ESPERA Y VUELVE A INTENTAR...");
            }
        } finally {
            mutexB.release(); //otro hilo puede seguir
        }
        return i;
    }

    public void terminarB(int num, String n) {
        System.out.println(n + " Termino de imprimir en impresora B " + num);
        tipoB[num].release();
    }

    public int[] imprimirC(String n) throws InterruptedException {
        //va a retornar -1 si no encontro ninguna impresora disponible
        boolean exito = false;
        int[] retorno = new int[2];
        retorno[0] = -1;
        retorno[1] = -1;
        int i;
        System.out.println(n + " ES DE TIPO C ");

        i = imprimirA(n);
        if (i >= 0) {
            exito = true;
            retorno[0] = 1;
            retorno[1] = i;
        } else {
            i = imprimirB(n);
            if (i >= 0) {
                exito = true;
                retorno[0] = 2;
                retorno[1] = i;
            }
        }
        return retorno;
    }

    public void terminarC(int[] r, String n) {
        System.out.println(n + " Termino de imprimir en impresora " + r[1]);
        if (r[0] == 1) {
            tipoA[r[1]].release();
        } else if (r[0] == 2) {
            tipoB[r[1]].release();

        }
    }
}
/*  GestionImpresoras impresoras = new GestionImpresoras();
        Cliente c1 = new Cliente(impresoras, "c1", "tipo A");
        Cliente c2 = new Cliente(impresoras, "c2", "tipo A");
        Cliente c3 = new Cliente(impresoras, "c3", "tipo B");
        Cliente c4 = new Cliente(impresoras, "c4", "cualquiera");
        Cliente c5 = new Cliente(impresoras, "c5", "tipo A");

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(c3);
        Thread t4 = new Thread(c4);
        Thread t5 = new Thread(c5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}

class Cliente implements Runnable {

    private GestionImpresoras recurso;
    private String nombre;
    private String tipo;

    public Cliente(GestionImpresoras r, String n, String t) {
        this.recurso = r;
        this.nombre = n;
        this.tipo = t;
    }

    public void run() {
        try {
            if (tipo.equals("tipo A")) {
                recurso.imprimirA(nombre);
            } else if (tipo.equals("tipo B")) {
                recurso.imprimirB(nombre);
            } else {
                recurso.imprimir(nombre);
            }
        } catch (InterruptedException e) {

        }
    }
}

class GestionImpresoras {

    private Semaphore impresorasA = new Semaphore(3);
    private Semaphore impresorasB = new Semaphore(2);

    public void imprimirA(String nombre) throws InterruptedException {
        impresorasA.acquire();

        System.out.println(nombre + " esta usando una impresora de tipo A");
        Thread.sleep(1000);
        System.out.println(nombre + " termino de imprimir");
        impresorasA.release();
    }

    public void imprimirB(String nombre) throws InterruptedException {
        impresorasB.acquire();

        System.out.println(nombre + " esta usando una impresora de tipo B");
        Thread.sleep(1000);
        System.out.println(nombre + " termino de imprimir");
        impresorasB.release();
    }

    public void imprimir(String nombre) throws InterruptedException {
        if (impresorasA.tryAcquire()) {
            System.out.println(nombre + " esta usando una impresora de tipo A");
            Thread.sleep(1000);
            System.out.println(nombre + " termino de imprimir");
            impresorasA.release();

        } else if (impresorasB.tryAcquire()) {
            System.out.println(nombre + " esta usando una impresora de tipo B");
            Thread.sleep(1000);
            System.out.println(nombre + " termino de imprimir");
            impresorasB.release();
        }
    }
}
 */
