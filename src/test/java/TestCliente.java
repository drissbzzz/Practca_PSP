
import com.mycompany.psp.Cliente;
import com.mycompany.psp.Peluquera;
import com.mycompany.psp.Peluqueria;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author driss
 */
public class TestCliente {
    
   public static void main(String[] args) {
        
        // Configuración de la prueba (tu petición: 50 clientes y 5 peluqueras)
        // NOTA: El enunciado original pide 3 peluqueras, puedes cambiarlo aquí.
        int NUM_PELUQUERAS = 3;
        int NUM_CLIENTES = 10;

        System.out.println("=== INICIO DE LA SIMULACIÓN MASIVA ===");
        System.out.println("Peluqueras: " + NUM_PELUQUERAS);
        System.out.println("Clientes: " + NUM_CLIENTES);
        System.out.println("--------------------------------------");

        // 1. Crear el recurso compartido (El edificio)
        Peluqueria laPeluqueria = new Peluqueria();

        // 2. Crear y lanzar las Peluqueras
        // Usamos un array para mantenerlas organizadas, aunque no es estricto.
        Peluquera[] peluqueras = new Peluquera[NUM_PELUQUERAS];
        
        for (int i = 0; i < NUM_PELUQUERAS; i++) {
            peluqueras[i] = new Peluquera(i + 1, laPeluqueria);
            
            // TRUCO: Las marcamos como 'Daemon'. 
            // Esto significa que si el programa principal acaba, estos hilos mueren solos.
            peluqueras[i].setDaemon(true); 
            peluqueras[i].start();
        }

        // 3. Crear y lanzar los Clientes
        Cliente[] clientes = new Cliente[NUM_CLIENTES];
        
        for (int i = 0; i < NUM_CLIENTES; i++) {
            clientes[i] = new Cliente(i + 1, laPeluqueria);
            clientes[i].start();
        }

        // 4. LA BARRERA: Esperar a que todos los clientes terminen
        // Si no hacemos esto, el main llegaría al final y cerraría el programa
        // dejando a los clientes a medias.
        for (int i = 0; i < NUM_CLIENTES; i++) {
            try {
                // .join() significa: "Main, espérate aquí quieto hasta que el cliente[i] termine"
                clientes[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. Cierre
        System.out.println("--------------------------------------");
        System.out.println("=== FIN DE LA SIMULACIÓN ===");
        System.out.println("Todos los clientes han sido atendidos. Cerrando local.");
        
        // Forzamos el cierre para asegurar que las peluqueras dejen de ejecutarse
        System.exit(0);
    }    
}
