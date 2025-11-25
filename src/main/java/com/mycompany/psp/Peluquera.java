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
    private Peluqueria peluqueria;

    public Peluquera(int id, Peluqueria peluqueria) {
        this.id = id;
        this.peluqueria = peluqueria;
    }

    public int getIdPeluquera() { return id; }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. Esperamos turno
                //System.out.println("... Peluquera " + id + " esta INACTIVA esperando clientes ...");
                peluqueria.esperarTimbre(); // Consume 1 ticket

                // 2. Intentamos trabajar
                boolean heTrabajado = false; // Marcador

                // Usamos el lock atómico (tryLock) en orden inverso de prioridad
                if (peluqueria.getPeinado().atender(this)) {
                    heTrabajado = true;
                }
                else if (peluqueria.getTinte().atender(this)) {
                    heTrabajado = true;
                }
                else if (peluqueria.getCorte().atender(this)) {
                    heTrabajado = true;
                }
                else if (peluqueria.getLavado().atender(this)) {
                    heTrabajado = true;
                }

                // 3. LA SOLUCIÓN AL BLOQUEO
                // Si consumí un ticket pero no pude trabajar (porque otra peluquera
                // me ganó el tryLock en todas las zonas), DEVUELVO EL TICKET.
                if (!heTrabajado) {
                    // "No he podido hacer nada, devuelvo el turno por si acaso"
                    peluqueria.tocarTimbre(); 
                    
                    // Pequeña pausa para no entrar en bucle infinito instantáneo (Yield)
                    Thread.sleep(10); 
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}