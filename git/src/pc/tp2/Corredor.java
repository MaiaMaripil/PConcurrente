/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

/**
 *
 * @author famil
 */
public class Corredor implements Runnable {

    private String nombre;
    private int distancia = 0;

    public Corredor(String n) {
        this.nombre = n;
    }
    public int getDistancia(){
        return distancia;
    }
    public String getDatos(){
        String n=(nombre+"| DISTANCIA TOTAL : "+distancia);
        return n;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            int numAleatorio = (int) (Math.random() * 10) + 1;
            System.out.println("Paso "+(i+1)+" de "+nombre+" |Distancia: "+numAleatorio);
            
            distancia = distancia + numAleatorio;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(nombre + " Interrumpido.");
            }
        }
        System.out.println("Termina thread " + nombre);
    }

    
}
 
