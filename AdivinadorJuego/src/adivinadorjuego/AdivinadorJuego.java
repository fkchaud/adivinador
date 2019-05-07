package adivinadorjuego;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdivinadorJuego {
    
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
            String str = br.readLine();
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
        Pensador p = new Pensador();
        while(true) {
            //System.out.println("Pensador piensa en: "+p.getNumero());
            System.out.println("Ingrese un numero de 4 digitos distintos:");
            String str = br.readLine();
            try {
                System.out.println(p.adivinarNumero(str));
                if (p.adivinarNumero(str).equals("4 bien, 0 regular")) break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("");
        }
    }

    private static void parteDos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
