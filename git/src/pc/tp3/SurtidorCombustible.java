/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp3;

/**
 *
 * @author famil
 */
public class SurtidorCombustible {

    public static void main(String[] args) {
        Surtidor s=new Surtidor(80);
        Auto[] autos= new Auto[5];
        
        for(int i=0;i<autos.length;i++){
            autos[i]=new Auto("Auto "+(i+1),0,1000,20,s);
            Thread t1= new Thread(autos[i],autos[i].getPatente());
            t1.start();
        }

    }
}

class Auto implements Runnable {

    String patente;
    int modelo;
    double km;
    int combustible;
    Surtidor surtidor; //recurso compartido

    public Auto(String p, int mod, double k, int comb, Surtidor s) {
        this.patente = p;
        this.modelo = mod;
        this.km = k;
        this.combustible = comb;
        this.surtidor = s;
    }
    
    public String getPatente(){
        return this.patente;
    }

    public void run() {
        try {
            int i = combustible;

            while (i > 10) {
                System.out.println("El auto " + patente + " esta recorriendo la ciudad");
                Thread.sleep(10);
                i -= 5;
            }

            System.out.println("El auto " + patente + " necesita cargar combustible");
            surtidor.cargarCombustible();

        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }
    }

}

class Surtidor {

    int capacidad;

    public Surtidor(int c) {
        this.capacidad = c;
    }

    public synchronized void cargarCombustible() throws InterruptedException {
        if (capacidad > 20) {
            System.out.println("El auto " + Thread.currentThread().getName() + " esta cargando combustible.");
            Thread.sleep(100);
            capacidad -= 20;
            System.out.println("Finalizo la recarga de combustible del auto+ " + Thread.currentThread().getName()
                    + " |LITROS DISPONIBLES EN EL SURTIDOR= " + capacidad);
        } else {
            System.out.println("No se pudo cargar combustible al auto+ " + Thread.currentThread().getName() + " "
                    + "| NO HAY SUFICIENTE COMBUSTIBLE");
        }

    }

}
