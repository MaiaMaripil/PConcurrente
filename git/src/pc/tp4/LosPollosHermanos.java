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
public class LosPollosHermanos {

    public static void main(String[] args) {
        Cafeteria rec=new Cafeteria();
        Thread mozo=new Thread(new Mozo("MOZO", rec));
        mozo.start();
        
        for(int i=0;i<5;i++){
            try{
                Thread t=new Thread(new Empleado("EMPLEADO "+(i+1),rec));
                
            t.start();
            Thread.sleep(((int)(Math.random()*5)+1)*1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
        
    }
}


class Empleado implements Runnable{
    String nombre;
    Cafeteria rC;
    
    public Empleado(String n, Cafeteria r){
        this.nombre=n;
        this.rC=r;
    }
    
    public void run(){
        try{
            boolean exito;
            
            exito=rC.irCafeteria(nombre);
            if(exito){
                Thread.sleep(1000);
                rC.elegirComida(nombre);
                Thread.sleep(3000);
                rC.terminarComer(nombre);
            }
            
            
        }catch (InterruptedException e){
            
        }
    }
    
}

class Mozo implements Runnable{
    String nombre;
    Cafeteria rC;
    
    public Mozo(String n, Cafeteria r){
        this.nombre=n;
        this.rC=r;
    }
    
    public void run(){
        try{
            while(true){
                rC.atender(nombre);
                Thread.sleep(2000);
                rC.servirComida(nombre);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        
    }
}


class Cafeteria{
    Semaphore disponible=new Semaphore(1); 
    Semaphore comidaServida=new Semaphore(0);
    Semaphore mostrarCarta=new Semaphore(0);
    Semaphore cliente= new Semaphore(0);
    Semaphore libre=new Semaphore(0);
    Semaphore hacerPedido=new Semaphore(0);
    
    public void atender(String n)throws InterruptedException{
        System.out.println(n+" INVENTANDO NUEVAS VERSIONES DE POLLO");
        cliente.acquire(); //espera a que llegue un cliente
        System.out.println(n+" ATENDIENDO A CLIENTE, muestra la carta...");
        mostrarCarta.release();
        hacerPedido.acquire();
        System.out.println(n+"PREPARANDO COMIDA...");
    }
    
    public void servirComida(String n) throws InterruptedException{
        System.out.println(n+" SIRVE LA COMIDA y espera...");
        comidaServida.release();
        
        libre.acquire(); //Espera a que el cliente se vaya
    }
    
    
    //empleado
    public boolean irCafeteria(String n) throws InterruptedException{
        boolean exito=false;
        if(disponible.tryAcquire()){
            exito=true;
            System.out.println(n+" ENCONTRO LUGAR, AVISA AL MOZO");
            cliente.release(); //avisa al mozo
            mostrarCarta.acquire(); //espera a que le muestre la carta
            System.out.println(n+" ELIGIENDO...");
        }else{
            System.out.println(n+" NO HAY LUGAR. SE RETIRA ...");
        }
        return exito;
                
    }
    
    public void elegirComida(String n)throws InterruptedException{
        System.out.println(n+" TERMINO DE ELEGIR, HACE SU PEDIDO y espera...");
        hacerPedido.release();
            comidaServida.acquire();
            System.out.println(n+"COMIENDO...");
    }
    
    public void terminarComer(String n){
        System.out.println(n+"AGRADECE AL MOZO Y SE VA");
        disponible.release();
        libre.release();
    }
}


     