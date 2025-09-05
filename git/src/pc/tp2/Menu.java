/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

import pc.tp2.Corredor;
import pc.tp2.Cajero;
import pc.tp2.Cliente;
import pc.tp2.CajeroThread;

/**
 *
 * @author famil
 */
public class Menu {

    public static void main(String[] args) {
         Corredor[] corredores = new Corredor[3];
        int mayorDist = 0;
        Corredor ganador=null;

        corredores[0] = new Corredor("Carla");
        corredores[1] = new Corredor("Lucia");
        corredores[2] = new Corredor("Sofia");

        Thread[] corredoresHilo = new Thread[corredores.length];
        for (int i = 0; i < corredores.length; i++) {
            corredoresHilo[i] = new Thread(corredores[i]);
            corredoresHilo[i].start();
        }

        try {
            for (int i = 0; i < corredores.length; i++) {
                //Espera a que terminen todos los hilos para seguir
                corredoresHilo[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < corredores.length; i++) {
            System.out.println(corredores[i].getDatos());
            if (corredores[i].getDistancia() > mayorDist) {
                mayorDist = corredores[i].getDistancia();
                ganador = corredores[i];
            }
        }
        System.out.println("Mayor distancia recorrida: " + ganador.getDatos());
         

 /*Cliente cliente1 = new Cliente("Cliente 1", new int[]{2, 2, 1, 5, 2, 3});
        Cliente cliente2 = new Cliente("Cliente 2", new int[]{1, 3, 5, 1, 1});
        Cajero cajero1 = new Cajero("Cajero 1");
        // Tiempo inicial de referencia
        long initialTime = System.currentTimeMillis();
        cajero1.procesarCompra(cliente1, initialTime);
        cajero1.procesarCompra(cliente2, initialTime);*/
 
 
        /*Cliente cliente1 = new Cliente("Cliente 1", new int[]{2, 2, 1, 5, 2, 3});
        Cliente cliente2 = new Cliente("Cliente 2", new int[]{1, 3, 5, 1, 1});
        
        CajeroThread c1= new CajeroThread("Cajero 1",cliente1 );
        CajeroThread c2= new CajeroThread("Cajero 2",cliente2);
        
        c1.start();
        c2.start();*/

    }
}
