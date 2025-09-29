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
public class EjericioParcial {

    public static void main(String[] arg) {
        Colectivo r = new Colectivo(4, 2);
        Thread chofer = new Thread(new Chofer("CHOFER", r));
        chofer.start();

        for (int i = 0; i < 6; i++) {
            try {
                Thread t = new Thread(new Pasajero("PASAJERO " + (i + 1), r));
                t.start();
                Thread.sleep((int) (Math.random() * 5000) + 1);

            } catch (InterruptedException s) {
                s.printStackTrace();
            }

        }
    }

}

class Chofer implements Runnable {

    String nombre;
    Colectivo rC;

    public Chofer(String n, Colectivo c) {
        this.nombre = n;
        this.rC = c;
    }

    public void run() {
        try {
            boolean lleno=false;
            while(rC.getCantR()>0){
                do{
                    lleno=rC.iniciarViaje(nombre);
                }while(!lleno);
                
                Thread.sleep(5000);
                rC.terminarViaje(nombre);
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}

class Pasajero implements Runnable {

    String nombre;
    Colectivo rC;

    public Pasajero(String n, Colectivo c) {
        this.nombre = n;
        this.rC = c;
    }

    public void run() {
        try {
            rC.subirColectivo(nombre);
            rC.bajarColectivo(nombre);

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}

class Colectivo {

    Semaphore puertaAdelante = new Semaphore(0);
    Semaphore puertaAtras = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);
    Semaphore subio = new Semaphore(0);
    Semaphore colectivoVacio = new Semaphore(0);
    private int capacidad, capAux, cantR;

    public Colectivo(int c, int cR) {
        this.capacidad = c;
        this.capAux = c;
        this.cantR = cR;
    }

    public void subirColectivo(String n) throws InterruptedException {
        System.out.println(n + " ESPERANDO PARA SUBIR");
        puertaAdelante.acquire();
        System.out.println(n + " SUBIO AL COLECTIVO");
        mutex.acquire();
        capAux--;
        subio.release();
        mutex.release();
    }

    public void bajarColectivo(String n) throws InterruptedException {

        puertaAtras.acquire();
        System.out.println(n + " BAJO DEL COLECTIVO");
        mutex.acquire();
        capAux++;
        if (capAux == capacidad) {
            colectivoVacio.release();
        } else {
            puertaAtras.release();
        }
        mutex.release();

    }

    public boolean iniciarViaje(String n) throws InterruptedException {
        boolean lleno = false;
        System.out.println(n + " HABILITA PUERTA DE ADELANTE");
        puertaAdelante.release();
        subio.acquire();
        mutex.acquire();
        
        if (capAux == 0) {
            lleno = true;
            System.out.println(n + " COLECTIVO LLENO. Iniciando viaje...");
        }
        mutex.release();

        return lleno;
    }
    
    public void terminarViaje(String n)throws InterruptedException{
        System.out.println(n + " TERMINO EL RECORRIDO");
        puertaAtras.release();
        colectivoVacio.acquire();
        cantR--;
        if(cantR==0){
            System.out.println(n + " SE HAN REALIZADO TODOS LOS RECORRIDOS");
        }
    }
    
    public int getCantR(){
        return this.cantR;
    }

}
