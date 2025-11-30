/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.psp;

import java.util.Scanner;

/**
 *
 * @author driss
 */
public class main {

    public static void main(String[] args) {

        int num_pelu = 3;
        int num_cli = 500; 
        
        //Para hacer los parametros de la peluqueria personalizables
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Introduce el numero de clientes que se simularan (Por defecto 500): "); String inputCli = sc.nextLine();
            if (!inputCli.isEmpty()) {
                num_cli = Integer.parseInt(inputCli);
            }
        } catch (NumberFormatException e) {
            System.out.println("Numero no válido. Usando valores por defecto.");
        }

        Peluqueria p = new Peluqueria(); //Creamos la centralita
        Peluquera[] pelu = new Peluquera[num_pelu];//Iniciamos las peluqueras
        for (int i = 0; i < num_pelu; i++) {
            pelu[i] = new Peluquera(i + 1, p);
            pelu[i].setDaemon(true);
            pelu[i].setName("Peluquera "+i);
            pelu[i].start();
        }
        Cliente[] clientes = new Cliente[num_cli]; //Iniciamos los clientes
        for (int i = 0; i < num_cli; i++) {
            clientes[i] = new Cliente(i + 1, p);
            clientes[i].setName("Cliente "+i);
            clientes[i].start();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            /**
             *Con esta linea lo que hago es monitorizar el ciclo de vida de mi main y le aviso de que 
             * antes de apagarse(ShutdownHook) tiene que realizar estas acciones usando el hilo que le 
             * he proporcionado
             */
            System.out.println("\n=== RANKING FINAL DE PELUQUERAS ===");
            Peluquera ganadora = null; //Creamos el hueco para la peluquera ganadora
            int maxClientes = -1;

            for (int i = 0; i < num_pelu; i++) {
                int total = pelu[i].getTotalClientesHistorico();
                System.out.println("Peluquera " + pelu[i].getIdPeluquera() + " atendió : " + total + " servicios.");
                Logger.escribir("Peluquera " + pelu[i].getIdPeluquera() + " atendió : " + total + " servicios.");                
                // Calculamos quién gana actualizando el MaxClientes y la peluquera por cada ciclo del bucle
                if (total > maxClientes) {
                    maxClientes = total;
                    ganadora = pelu[i];
                }
            }
            if (ganadora != null) {
                System.out.println("La peluquera que mas servicios realizo es: " + ganadora.getIdPeluquera());
                Logger.escribir("La peluquera que mas servicios realizo es: " + ganadora.getIdPeluquera()); 
            }
            Logger.escribir("Jornada finalizada. Ranking generado.");
            System.out.println("Jornada finalizada. Ranking generado.");
        }));
    }
  }
