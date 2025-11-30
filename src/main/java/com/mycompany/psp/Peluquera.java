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
    private int totalClientesHistorico = 0;
    private int limiteCansancio = (int) (Math.random() * 5 + 8); //Limite de cansacio aleatorio inicial

    public Peluquera(int id, Peluqueria p) {
        this.id = id;
        this.p = p;
    }

    public int getIdPeluquera() {
        return id;
    }

    public int getTotalClientesHistorico() {
        return totalClientesHistorico;
    }
    

    public void run() {
        while (true) { //Las hicimos Daemos para que muriese el hilo cuando no haya otros de mayor importancia
            try {
                //Dormidas y esperando que un cliente se siente
                p.esperarTimbre();

                boolean heTrabajado = false; //Para saber si completaron un servicio

                // Al llegar un cliente, comprueban si pueden atender en las zonas
                if (p.getPeinado().atender(this)) {
                    heTrabajado = true;
                } else if (p.getTinte().atender(this)) {
                    heTrabajado = true;
                } else if (p.getCorte().atender(this)) {
                    heTrabajado = true;
                } else if (p.getLavado().atender(this)) {
                    heTrabajado = true;
                }
                //Evitar bloqueos e inancion
                if (!heTrabajado) {// Si no ha conseguido trabajar
                    p.tocarTimbre(); //Devuelve el ticket que cogió para que el cliente que lo lanzó no lo pierda
                    //Se duerme una fraccion de segundo para evitar entrar en un bucle continuo
                    Thread.sleep(10);
                } else {
                    clientesAtendidos++;//Para contabilizar el sistema de siestas
                    totalClientesHistorico++;//Para el ranking
                    if (clientesAtendidos >= limiteCansancio) {
                        Logger.escribir("Peluquera " + id + " se toma una siesta...");
                        System.out.println("Peluquera " + id + " se toma una siesta...");
                        // Estado: SLEEPING (Morado) -> Siesta larga
                        Thread.sleep((long) (Math.random() * 2000 + 1000)); //Simulamos el tiempo de la siesta
                        Logger.escribir("Peluquera " + id + " se toma una siesta...");
                        System.out.println("Peluquera " + id + " vuelve al trabajo.");                       
                        clientesAtendidos = 0; //Reseteamos la variable para la siesta
                        limiteCansancio = (int) (Math.random() * 5 + 8); //Nuevo limite para la siguiente siesta
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
