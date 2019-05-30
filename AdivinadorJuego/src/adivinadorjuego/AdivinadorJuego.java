package adivinadorjuego;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AdivinadorJuego {
    
    static Scanner in = new Scanner(System.in);

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("¿Jugar la Parte 1 o Parte 2?");
        System.out.println("Parte 1: computadora piensa, humano adivina");
        System.out.println("Parte 2: humano piensa, computadora adivina");
        System.out.println("Ingrese 1 o 2");
        boolean valido = false;
        do {
            String str = in.next();
            if (str.equals("1")) {
                valido = true;
                System.out.println("");
                parteUno();
            } else if (str.equals("2")) {
                valido = true;
                System.out.println("");
                parteDos();
            } else {
                System.out.println("Ingreso inválido. Ingrese 1 o 2");
            }
        } while (!valido);
    }
    
    public static void parteUno() throws IOException {
        // Se instancia un objeto Pensador, que piensa un número
        Pensador p = new Pensador();
        
        // Se pregunta infinitas veces hasta que el jugador descubra el número correcto
        while(true) {
            System.out.println("Ingrese un numero de 4 digitos distintos:");
            // La validación se hace dentro de p.adivinarNumero(str)
            String str = in.next();
            try {
                // Muestra el resultado del intento
                System.out.println(p.adivinarNumero(str));
                // Si acertó 4, se termina el juego
                if (p.adivinarNumero(str).equals("4 bien, 0 regular")) {
                    System.out.println("Juego terminado!");
                    break;
                }
                // Si no acertó 4, sigue jugando
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("");
        }
    }

    private static void parteDos() throws IOException {
        // Se instancia el Adivinador
        Adivinador a = new Adivinador();
        // Inicia el proceso de adivinación
        if (a.adivinar()) {
            System.out.println("Juego terminado!");
        } else {
            System.out.println("Algo falló. No se encontró tu número.");
        }
    }
}
