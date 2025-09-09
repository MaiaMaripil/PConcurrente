/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp3;

/**
 *
 * @author famil
 */
public class Energia {

    public static void main(String[] args) {
        VerificarEnergia vE = new VerificarEnergia();
        Thread sanador = new Thread(vE, "Sanador");
        Thread criaturaOscura = new Thread(vE, "Criatura Oscura");
        sanador.start();
        criaturaOscura.start();

    }
}

class REnergia {

    private int valor = 10;

    public REnergia() {
    }

    public int getValor() {
        return this.valor;
    }

    public void quitarEnergia(int unValor) {
        this.valor = this.valor - unValor;

    }

    public void sumarEnergia(int unValor) {
        this.valor = this.valor + unValor;

    }
}

class VerificarEnergia implements Runnable {

    private REnergia energia = new REnergia();

    private synchronized void quitarEnergia(int valor) throws InterruptedException {

        if (energia.getValor() >= valor) {
            System.out.println(Thread.currentThread().getName() + " ha drenado " + valor + " de energia");
            Thread.sleep(1000);
            energia.quitarEnergia(valor);

            System.out.println("Energia total: " + energia.getValor());
        } else {
            System.out.println("No hay energia suficiente.");
            Thread.sleep(1000);
        }
    }

    private synchronized void sumarEnergia(int valor) throws InterruptedException {

        System.out.println(Thread.currentThread().getName() + " ha sumado " + valor + " de energia");
        Thread.sleep(100);
        energia.sumarEnergia(valor);
        System.out.println("Energia total: " + energia.getValor());

    }

    public void run() {
        for (int i = 0; i <= 2; i++) {
            try {
                if (Thread.currentThread().getName().equals("Criatura Oscura")) {
                    this.quitarEnergia(1);
                } else if (Thread.currentThread().getName().equals("Sanador")) {
                    this.sumarEnergia(3);
                }

            } catch (InterruptedException ex) {
                System.out.println("Error");
            }
        }
    }
}
