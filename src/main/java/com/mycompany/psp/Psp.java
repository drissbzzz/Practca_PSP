/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.psp;

/**
 *
 * @author driss
 */
public class Psp {

    public static void main(String[] args) {
        
        int num_pelu = 3;
        int num_cli = 500;

        System.out.println("Comienzo de la jornada en Peluquerias Paquita");        
        Peluqueria p = new Peluqueria();

        // Guardamos las peluqueras en un array para poder cerrarlas luego
        Peluquera[] pelu = new Peluquera[num_pelu];
        for (int i = 0; i < num_pelu; i++) {
            pelu[i] = new Peluquera(i + 1, p);
            pelu[i].start();
        }
        Cliente[] clientes = new Cliente[num_cli];
        for (int i = 0; i < num_cli; i++) {
            clientes[i] = new Cliente(i + 1, p);
            clientes[i].start();
        }
        //Esperamos a que los clientes completen su ciclo de vida 
        for (int i = 0; i < num_cli; i++) {
            try {
                clientes[i].join();
            } catch (InterruptedException e) {
                System.out.println("Eror en el cliente con id "+i+1);
            }
        }       
        System.out.println("Todos los clientes han sido atendidos.");
        System.out.println("Cierre de la jornada en Peluquerias Paquitas");      
        for (int i = 0; i < num_pelu; i++) {
            // Interrumpimos el hilo para que entre por el InterruptedException del hilo de las peluqueras
            pelu[i].interrupt(); 
        }       
        System.out.println("Se termino el programa");
    }
  }
