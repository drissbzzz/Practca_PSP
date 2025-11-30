/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Esta es una clase residual que hice para probar el funcionamiento de una manera cuando me dijiste lo de los colores,
 * hay un error en ella que hace que se bloqueen los hilosy se produzca inanicion en el lavado y las peluqueras se mueren o bloquean
 * Me gustaria revisarla para entender mejor que error esta pasando aqui
 * @author driss
 */
public class SitioAnticuado {

    private Peluqueria p;
    private String nombre;

    private final ReentrantLock llaveSitio = new ReentrantLock();

    private final Condition esperandoSitio = llaveSitio.newCondition();
    private final Condition recibiendoAtencion = llaveSitio.newCondition();

    // Estado del sitio
    private boolean ocupado = false;
    private boolean servicioTerminado = false;

    public SitioAnticuado(String nombre, Peluqueria p) {
        this.nombre = nombre;
        this.p = p;
    }

    public void entrar(Cliente c) {
        llaveSitio.lock(); // Entro al sitio
        try {
            //Mientras esté ocupado, espero (await).
            while (ocupado) {
                try {
                    esperandoSitio.await();
                } catch (InterruptedException e) {
                    System.out.println("Error en ocupar el sitio");
                }
            }
            // Avisaron de que puedo entrar.
            Logger.escribir("Cliente " + c.getIdCliente() + " se sienta en " + nombre + " y espera...");
            ocupado = true;  // Ocupo la silla
            servicioTerminado = false;// Comienzo a recibir atencion de la peluquera
            // Aviso a todas las peluqueras de que estoy ahi
            p.tocarTimbre();
            // Espero a que la peluquera me pelukie
            while (!servicioTerminado) {
                try {
                    recibiendoAtencion.await(); // Espero notificación de la peluquera
                } catch (InterruptedException e) {
                    System.out.println("Error en que la peluquera me suelte");
                }
            }
            // Termino en este sitio
            Logger.escribir("Cliente " + c.getIdCliente() + " sale de " + nombre);
            ocupado = false; //Aviso de que no hay nadie           
            // Aviso al siguiente cliente que esté esperando 
            esperandoSitio.signal();
        } finally {
            llaveSitio.unlock(); // Suelto la llave para que otro la coja
        }
    }

    public boolean atender(Peluquera pelu) {
        // Intenta dar atención en el sitio
        if (llaveSitio.tryLock()) {
            try {
                // Si no hay cliente me voy.
                if (!ocupado) {
                    return false;
                }
                Logger.escribir("Peluquera " + pelu.getIdPeluquera() + " atendiendo en " + nombre);
                try {
                    Thread.sleep((long) (Math.random() * 4000 + 2000));
                } catch (InterruptedException e) {
                    System.out.println("Error en atender al cliente");
                }
                servicioTerminado = true; // Aviso de que el servicio ya está realizado              
                recibiendoAtencion.signal();// Despierto al cliente
                return true;
            } finally {
                llaveSitio.unlock();//Libero el puesto para que entre otra peluquera
            }
        }
        return false;
    }
}
