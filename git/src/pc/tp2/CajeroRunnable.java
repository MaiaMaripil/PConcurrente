/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

/**
 *
 * @author famil
 */
public class CajeroRunnable {

    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Cliente 1", new int[]{2, 2, 1, 5, 2, 3});
        Cliente cliente2 = new Cliente("Cliente 2", new int[]{1, 3, 5, 1, 1});

        Thread h1 = new Thread(new Cajero("Cajera 1", cliente1));
        Thread h2 = new Thread(new Cajero("Cajera 2", cliente2));

        h1.start();
        h2.start();
    }
}

class Cajero implements Runnable {

    private String nombre;
    private Cliente cliente;
    private long initialTime = System.currentTimeMillis();

    public Cajero(String n, Cliente cl) {
        this.nombre = n;
        this.cliente = cl;
    }

    public void esperarXsegundos(int s) {
        try {
            Thread.sleep(s * 1000);//convierte s a msS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
}

class Cliente {

    private String nombre = "";
    private int[] carroCompra = null;

    public Cliente(String n, int[] carro) {
        this.nombre = n;
        this.carroCompra = carro;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int[] getCarroCompra() {
        return this.carroCompra;
    }
}
