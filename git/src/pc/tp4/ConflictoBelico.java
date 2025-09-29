/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp4;

/**
 *
 * @author famil
 */
public class ConflictoBelico {
public static void main (String[] arg){
    Cuartel c=new Cuartel(6);
    for(int i=0;i<6;i++){
        Thread t=new Thread(new Division(c, (i+1)));
        t.start();
    }
}
}

class Division implements Runnable {

    int id;
    Cuartel rC;

    public Division(Cuartel p, int d) {

        this.rC = p;
        this.id = d;
    }

    public void run() {
        try {
            //solo un hilo puede acceder al cuartel
           int o = 0;
        while (o < 2) { // imprimir exactamente 2 oraciones
            synchronized (rC) {
                if (rC.miTurno(id)) {
                    rC.mostrar();
                    o++;
                }
            }
            Thread.sleep(100); // reintento si no fue mi turno
        }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Cuartel {

    int turno = 1;
    int div;
    
    public Cuartel(int d){
        div=d;
    }

    public synchronized boolean miTurno(int id) {
        boolean exito = false;
        if (id == turno || turno==((div*2)+1)-id) {
            exito = true;
            System.out.println("ES EL TURNO DIVISION: " + id);
        }
        return exito;
    }

    public synchronized void mostrar() {
        System.out.println("ORACION " + turno);
        turno++;
    }

}
