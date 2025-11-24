
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

/**
 *
 * @author driss
 */
public class Cliente extends Thread {

    private int id;
    private Peluqueria peluqueria;

    public Cliente(int id, Peluqueria peluqueria) {
        this.id = id;
        this.peluqueria = peluqueria;
    }
    
    public int getIdCliente() {
        return id;
    }

    @Override
    public void run() {
        // Fase 1: Lavado
        // Al llamar a entrar(), el hilo se bloqueará si está ocupado 
        // y no pasará a la siguiente línea hasta que le hayan atendido.
        peluqueria.getLavado().entrar(this);
        peluqueria.getCorte().entrar(this);
        peluqueria.getTinte().entrar(this);
        peluqueria.getPeinado().entrar(this);
        System.out.println("--- Cliente " + id + " HA TERMINADO ---");
    }
}
