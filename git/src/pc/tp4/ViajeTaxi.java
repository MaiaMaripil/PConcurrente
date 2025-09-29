/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp4;
import java.util.concurrent.Semaphore;
/**
 *
 * @author famil
 */
public class ViajeTaxi {
    public static void main (String[] arg){
       Viaje rec=new Viaje(); 
       Thread taxista=new Thread(new Taxista("TAXISTA",rec));
       Thread cliente=new Thread(new Cliente("PASAJERO", rec));
     
      taxista.start();
      cliente.start();
      
      
      /*for(int i=0; i<5; i++){
          Thread t1=new Thread(new Cliente("Pasajero "+(i+1), rec));
          t1.start();
      }*/
    }
    
    
    
}

class Cliente implements Runnable{
    String nombre;
    Viaje rC;
    
    public Cliente(String n, Viaje r){
        this.nombre=n;
        this.rC=r;
    }
    
    @Override
    public void run(){
        try{
            rC.viajar(nombre);
        }catch(InterruptedException e){
            System.out.println("ERROR");
        }
    }
    
}

class Taxista implements Runnable{
    String nombre;
    Viaje rC;
    
    public Taxista(String n,Viaje r ){
        this.nombre=n;
        this.rC=r;
    }
    
    @Override
    public void run(){
        try{
            while(true){
                rC.manejar(nombre);
                Thread.sleep((int) (Math.random()*5)+1);
                rC.terminarViaje(nombre);
            }
        }catch(InterruptedException e){
            System.out.println("ERROR");
        }
        
    }
}

class Viaje{
    Semaphore pedirTaxi= new Semaphore(0); //lo usa el cliente para despertar al taxista
    Semaphore viajeTerminado=new Semaphore(0);
    Semaphore mutex=new Semaphore(1);
    
    
    public void manejar(String n) throws InterruptedException{
        System.out.println(n+" DURMIENDO, en espera de cliente...");
        pedirTaxi.acquire();
        
        System.out.println(n+" INICIA VIAJE");
        
    }
    
    public void terminarViaje(String n){
        System.out.println(n+" VIAJE FINALIZADO, avisa al pasajero...");
        viajeTerminado.release();
        
    }
    
    public void viajar(String n) throws InterruptedException{
        
        System.out.println(n+" EN BUSCA DE TAXI, avisa al taxista...");
        pedirTaxi.release();
        System.out.println(n+" ESPERANDO A LLEGAR A DESTINO");
        viajeTerminado.acquire();
        System.out.println(n+" LLEGUE A DESTINO.");
       
        
    }
    
}