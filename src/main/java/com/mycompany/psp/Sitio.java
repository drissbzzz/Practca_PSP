/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

import java.util.concurrent.Semaphore;

public class Sitio {

    private Peluqueria p;
    private String nombre;
    private Semaphore silla = new Semaphore(1);
    private Semaphore avisoHayCliente = new Semaphore(0);
    private Semaphore finServicio = new Semaphore(0);
    private boolean ocupado = false;

    public Sitio(String nombre, Peluqueria p) {
        this.nombre = nombre;
        this.p = p;
    }
    
    public boolean estaOcupado() { return ocupado; }
    
    public void entrar(Cliente c) {
        try {
            silla.acquire();  // 1. Me siento (si está ocupado, espero aquí) 
            ocupado = true;
            System.out.println("Cliente " + c.getIdCliente() + "SE SIENTA en " + nombre + " y espera...");           
            p.tocarTimbre();// 2. Aviso de que estoy esperando
            finServicio.acquire();     // 3. Me echo a dormir hasta que terminen
            System.out.println("Cliente " + c.getIdCliente() + "SALE de " + nombre);
            silla.release();           // 4. Me voy y libero la silla
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void atender(Peluquera p) {
        try {
            System.out.println("Peluquera "+ p.getIdPeluquera() +" ATENDIENDO en " + nombre);
            Thread.sleep(100); // espero           
            finServicio.release(); // 2. Despierto al cliente: "Ya he acabado"
            ocupado = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
