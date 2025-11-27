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
        while (true) { // Como es Daemon, este while morirá solo
            try {
                // Estado: WAIT (Naranja) -> Esperando ticket
                p.esperarTimbre();

                boolean heTrabajado = false;

                // Estado: RUNNING (Verde) -> Buscando trabajo
                if (p.getPeinado().atender(this)) {
                    heTrabajado = true;
                } else if (p.getTinte().atender(this)) {
                    heTrabajado = true;
                } else if (p.getCorte().atender(this)) {
                    heTrabajado = true;
                } else if (p.getLavado().atender(this)) {
                    heTrabajado = true;
                }

                if (!heTrabajado) {
                    p.tocarTimbre();
                    // Estado: SLEEPING (Morado) -> Pequeña pausa
                    Thread.sleep(10);
                } else {
                    clientesAtendidos++;
                    totalClientesHistorico++;
                    if (clientesAtendidos >= 10) {
                        Logger.escribir("Peluquera " + id + " se toma una siesta...");
                        // Estado: SLEEPING (Morado) -> Siesta larga
                        Thread.sleep((long) (Math.random() * 2000 + 1000));
                        System.out.println("Peluquera " + id + " vuelve al trabajo.");
                        clientesAtendidos = 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
