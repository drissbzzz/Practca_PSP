/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.psp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author driss
 */
public class Logger {

    private static String archivo = "registro_peluqueria.txt";

    public static synchronized void escribir(String mensaje) {

        LocalDateTime fechaHora = LocalDateTime.now();
        String lineaLog = "[" + fechaHora + "] " + mensaje;
        try (FileWriter fw = new FileWriter(archivo, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(lineaLog);
        } catch (IOException e) {
            System.err.println("Error escribiendo en el log: " + e.getMessage());
        }
    }
}
    
 
