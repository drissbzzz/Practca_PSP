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

    public boolean estaOcupado() {
        return ocupado;
    }

    public void entrar(Cliente c) {
        try {
            silla.acquire();  // Cliente coge un ticket del semaforo de la silla            
            System.out.println("Cliente " + c.getIdCliente() + " espera atencion en: " + nombre);
            ocupado = true; //Para que la peluquera antes de actuar confirme que hay un cliente
            p.tocarTimbre();// Cliente libera un ticket general para indicar que hay trabajo pendiente
            finServicio.acquire(); //Intenta adquirir un ticket de servicio terminado
            System.out.println("Cliente " + c.getIdCliente() + " termino en: " + nombre);
            silla.release(); //Libera el ticket para que otro pueda ocupar la silla
        } catch (InterruptedException e) {
            System.out.println("Error en la entrada del cliente");
        }
    }

    public boolean atender(Peluquera p) {

        if (cerrojoPeluquera.tryLock()) { //La peluquera intenta coger la llave a la atencion
            try {
                if (!ocupado) { // Se vuelve a comprobar si realmente hay un cliente esperando atencion
                    return false; //Devuelve falso y para aqui el metodo de atender
                }
                // Trabajando sobre el cliente
                Logger.escribir("Peluquera " + p.getIdPeluquera() + " trabajando en: " + nombre);
                try {
                    Thread.sleep((long) (Math.random() * 4000 + 1000));// Tiempo aleatorio de descanso entre 1 y 5 segundos
                } catch (Exception e) {
                    System.out.println("Error en el sleep de la peluquera");
                }
                ocupado = false; // La peluquera termina de atender y pone como que el sitio ya no está ocupado
                finServicio.release();
                /*Una vez la peluquera ya ha terminado el cliente ya no esta bloqueado
                y le da el ticket que estaba esperando para irse, así ambos duran lo mismo en el proceso de la atencion*/
                return true; // Se realizo la atención correctamente
            } finally {
                cerrojoPeluquera.unlock(); // La peluquera desbloquea el metodo de atender de la zona
            }
        }
        return false; //Si no consigue el lock, no se pudo realizar la atencion
    }

}
