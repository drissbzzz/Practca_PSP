/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Sitio {

    private Peluqueria p;
    private String nombre;
    private Semaphore silla = new Semaphore(1);
    private ReentrantLock cerrojoPeluquera = new ReentrantLock();
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
            System.out.println("Cliente " + c.getIdCliente() + "SE SIENTA en " + nombre + " y espera...");
            ocupado = true;
            p.tocarTimbre();// 2. Aviso de que estoy esperando
            finServicio.acquire();     // 3. Me echo a dormir hasta que terminen
            System.out.println("Cliente " + c.getIdCliente() + "SALE de " + nombre);
            silla.release();           // 4. Me voy y libero la silla
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean atender(Peluquera p) {
        // 1. Intento cerrar el candado (tryLock). 
        // Si devuelve true: He conseguido entrar yo sola.
        // Si devuelve false: Hay otra peluquera, así que me voy sin bloquearme.
        if (cerrojoPeluquera.tryLock()) {
            try {
                // 2. Comprobación de seguridad: ¿De verdad hay cliente?
                // Si entré pero resulta que no hay nadie (falsa alarma), devuelvo false.
                if (!ocupado) {
                    return false; 
                }
                
                // 3. SI LLEGO AQUÍ, HE CONSEGUIDO EL TRABAJO
                System.out.println("Peluquera "+ p.getIdPeluquera() +" ATENDIENDO en " + nombre);
                try {
                    Thread.sleep(100); 
                } catch (Exception e) {}
                
                ocupado = false; // Ya no está ocupado
                finServicio.release(); // Despierta al cliente
                
                return true; // ÉXITO: He trabajado
                
            } finally {
                // IMPORTANTE: Siempre abrir el cerrojo al salir
                cerrojoPeluquera.unlock();
            }
        }
        return false;
    }

}
