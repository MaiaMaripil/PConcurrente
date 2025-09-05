/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

import pc.tp2.Cliente;

/**
 *
 * @author famil
 */
public class CajeroThread extends Thread {

    private String nombre;
    private Cliente cliente;
    private long initialTime = System.currentTimeMillis();

    public CajeroThread(String n, Cliente cl) {
        this.nombre = n;
        this.cliente = cl;

    }

    public void run() {
        System.out.println("El cajero " + this.nombre
                + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
                + this.cliente.getNombre() + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
        for (int i = 0; i < this.cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) + " del cliente " + this.cliente.getNombre() + "->Tiempo:"
                    + (System.currentTimeMillis() - this.initialTime) / 1000 + " seg");
        }
        System.out.println("El cajero " + this.nombre + " HA TERMINADO DE PROCESAR " + this.cliente.getNombre() + " EN EL TIEMPO:"
                + (System.currentTimeMillis() - this.initialTime) / 1000 + " seg");

    }

    public void esperarXsegundos(int s) {
        try {
            Thread.sleep(s * 1000);//convierte s a ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
