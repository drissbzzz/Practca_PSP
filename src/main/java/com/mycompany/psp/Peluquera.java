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
    private int clientesAtendidos = 0; // Contador para la siesta

    public Peluquera(int id, Peluqueria peluqueria) {
        this.id = id;
        this.peluqueria = peluqueria;
    }

    public int getIdPeluquera() {
        return id;
    }
    
    @Override
    public void run() {
        while (true) { 

            System.out.println("... Peluquera " + id + " esta INACTIVA esperando clientes ...");
            peluqueria.esperarTimbre();

            // 2. BUSCAR EL TRABAJO (Mirando los booleans)         
           // MIRA EL FINAL PRIMERO
            if (peluqueria.getPeinado().estaOcupado()) {
                peluqueria.getPeinado().atender(this);
                clientesAtendidos++;
            } else if (peluqueria.getTinte().estaOcupado()) {
                peluqueria.getTinte().atender(this);
                clientesAtendidos++;
            } else if (peluqueria.getCorte().estaOcupado()) {
                peluqueria.getCorte().atender(this);
                clientesAtendidos++;
            } // MIRA EL PRINCIPIO AL FINAL
            else if (peluqueria.getLavado().estaOcupado()) {
                peluqueria.getLavado().atender(this);
                clientesAtendidos++;
            }
                
                if (clientesAtendidos == 10) {
                    System.out.println("Peluquera " + id + " se toma una siesta");
                    try {
                        // Dormimos un tiempo aleatorio SIN bloquear ningún recurso
                        Thread.sleep((long) (Math.random() * 2000 + 500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Peluquera " + id + " vuelve al trabajo.");
                    clientesAtendidos = 0;
                }
            }
        }
    }           
