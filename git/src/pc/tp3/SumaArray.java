/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp3;

import java.util.Random;

/**
 *
 * @author famil
 */
public class SumaArray {

    public static void main(String[] arg) {
        int[] array = new int[50000];
        Random random = new Random();
        SumaTotal sumaT = new SumaTotal();

        SumaParcial[] hilos = new SumaParcial[10];
        Thread[] t=new Thread[hilos.length];
        int k = hilos.length;
        int tamanioSeg = array.length / k;

        for (int i = 0; i < array.length; i++) {
            int aux = random.nextInt(10) + 1;
            array[i] = aux;
        }

        for (int j = 0; j < k; j++) {
            int inicio = j * tamanioSeg;
            int fin;
            if (j == k-1) {
                fin = array.length;
            } else {
                fin = inicio + tamanioSeg;
            }

            hilos[j] = new SumaParcial(array, inicio, fin, sumaT);
            t[j]=new Thread(hilos[j],"Suma "+(j+1));
            t[j].start();
            
        }
        
        for(int i=0;i<k;i++){
            try{
            t[i].join();
            }catch(InterruptedException e){
                System.out.println("ERROR");
            }
        }
        
        System.out.println("SUMA TOTAL= "+ sumaT.getTotal());

    }
    
}

class SumaParcial implements Runnable {

    int[] array;
    int inicio, fin;
    int suma;
    SumaTotal sTotal;

    public SumaParcial(int[] a, int i, int f, SumaTotal s) {

        this.array = a;
        this.inicio = i;
        this.fin = f;
        this.suma = 0;
        this.sTotal = s;
    }

    public void sumar() {
        for(int i=inicio;i<fin;i++){
            suma += array[i];
        }
    }

    public void run() {
        sumar();
        sTotal.sumar(suma);
        
    }
}

class SumaTotal {

    int total;

    public SumaTotal() {
        this.total = 0;
    }
    
    public int getTotal(){
        return total;
    }

    public synchronized void sumar(int sumaParcial) {
        System.out.println(Thread.currentThread().getName()+"= "+sumaParcial);
        this.total += sumaParcial;
    }
}
