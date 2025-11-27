/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.psp;

/**
 *
 * @author driss
 */
public class Psp {

    public static void main(String[] args) {

        int num_pelu = 3;
        int num_cli = 10; 

        Peluqueria p = new Peluqueria();
        Peluquera[] pelu = new Peluquera[num_pelu];
        for (int i = 0; i < num_pelu; i++) {
            pelu[i] = new Peluquera(i + 1, p);
            pelu[i].setDaemon(true);
            pelu[i].setName("Peluquera "+i);
            pelu[i].start();
        }
        Cliente[] clientes = new Cliente[num_cli];
        for (int i = 0; i < num_cli; i++) {
            clientes[i] = new Cliente(i + 1, p);
            clientes[i].setName("Cliente "+i);
            clientes[i].start();
        }

        System.out.println("-");
    }
  }
