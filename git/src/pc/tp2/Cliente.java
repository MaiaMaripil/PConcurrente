/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pc.tp2;

/**
 *
 * @author famil
 */
public class Cliente {
    private String nombre;
    private int[] carroCompra;
    
    public Cliente(String n, int[] carro){
        this.nombre=n;
        this.carroCompra=carro;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int[] getCarroCompra(){
        return this.carroCompra;
    }
}
