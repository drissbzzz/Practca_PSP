/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

/**
 *
 * @author driss
 */
public class Peluquera extends Thread {

    private int id;
    private Peluqueria p;
    private int clientesAtendidos = 0;

    public Peluquera(int id, Peluqueria p) {
        this.id = id;
        this.p = p;
    }

    public int getIdPeluquera() {
        return id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                p.esperarTimbre(); // Intenta coger un ticket de los que libera el cliente
                boolean heTrabajado = false; // Creamos este boolean para evitar falsos positivos de cumplir la accion de atender

                // Usamos el lock
                if (p.getPeinado().atender(this)) {
                    heTrabajado = true;
                } else if (p.getTinte().atender(this)) {
                    heTrabajado = true;
                } else if (p.getCorte().atender(this)) {
                    heTrabajado = true;
                } else if (p.getLavado().atender(this)) {
                    heTrabajado = true;
                }

                // Si consumio un ticket pero no pudo atemder (porque otra peluquera
                // le gano el tryLock en todas las zonas), devuelve el ticket a la zona para que no se quede ese cliente sin atencion.
                // Esto se debe hacer porque sino el cliente que lanza la peticion de atención se queda sin su ticket ya que esta peluquera
                // lo consume y no realiza la accion
                if (!heTrabajado) {
                    p.tocarTimbre();
                    Thread.sleep(10);//La peluquera se va a dormir un pequeño momento para evitar que vuelva a coger el mismo ticket que ella dejo y
                    // que vuelva a pasar lo mismo constantemente
                } else {
                    clientesAtendidos++; //comprobamos clientes atendidos para el sistema de las siestas
                    if (clientesAtendidos >= 10) {
                        System.out.println("Peluquera " + id + " se toma una siesta para descansar...");
                        Thread.sleep((long) (Math.random() * 2000 + 1000));// Tiempo aleatorio de descanso entre 1 y 3 segundos
                        System.out.println("Peluquera " + id + " vuelve al trabajo de su siesta.");
                        clientesAtendidos = 0; // Reset del contador
                    }
                }
            } catch (InterruptedException e) {
                // En caso de interrupción
                System.out.println("Peluquera " + id + " termina su jornada y se va a casa.");
                return; // Rompe el bucle y finaliza el hilo
            } catch (Exception e) {
                System.out.println("Error en el run de las peluqueras");
            }
        }
    }
}
