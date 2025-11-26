/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

import java.util.concurrent.Semaphore;

public class Peluqueria {

    private Sitio lavado;
    private Sitio corte;
    private Sitio tinte;
    private Sitio peinado;

    private Semaphore timbreGeneral = new Semaphore(0);

    public Peluqueria() {
        this.lavado = new Sitio("Lavado", this);
        this.corte = new Sitio("Corte", this);
        this.tinte = new Sitio("Tinte", this);
        this.peinado = new Sitio("Peinado", this);
    }

    public Sitio getLavado() {
        return lavado;
    }

    public Sitio getCorte() {
        return corte;
    }

    public Sitio getTinte() {
        return tinte;
    }

    public Sitio getPeinado() {
        return peinado;
    }

    // El cliente llama a esto cuando se sienta en cualquier zona
    public void tocarTimbre() {
        timbreGeneral.release(); // Se activa el timbre para despertar a una peluquera
    }

    // La peluquera llama a esto para echarse la siesta (pero no la que le corresponde) hasta que haya trabajo
    public void esperarTimbre() {
        try {
            timbreGeneral.acquire(); // Se duerme si es 0. Despierta si es 1.
        } catch (InterruptedException e) {
            System.out.println("Error en el ticket general");
        }
    }
}
