package adivinadorjuego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Adivinador {
    
    private ArrayList<Intento> intentos = new ArrayList<>();

    public Adivinador() {
    }

    private String pensarNumero() {
        int numero;
        String str = "";
        boolean valido = false;
        
        while (!valido) {
            numero = (int) (Math.random() * 9877);
            if (numero < 10) str = "000"+numero;
            else if (numero < 100) str = "00"+numero;
            else if (numero < 1000) str = "0"+numero;
            else str = ""+numero;
            valido = esValido(str);
        }   
        
        return str;
    }

    public boolean adivinar() throws IOException {
        
        String intento;
        int bien;
        int regular;
        
        intento = pensarNumero();
        System.out.println("¿El número es "+intento+"?");
        System.out.println("Números bien:");
        while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
            System.out.println("Ingrese un número de 0 a 5:");
            AdivinadorJuego.in.next();
        }
        bien = AdivinadorJuego.in.nextInt();
        System.out.println("Números regular:");
        while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
            System.out.println("Ingrese un número de 0 a 5:");
            AdivinadorJuego.in.next();
        }
        regular = AdivinadorJuego.in.nextInt();

        if (bien == 4) {
            System.out.println("La respuesta es "+intento);
            return true;
        } else {
            intentos.add(new Intento(intento,bien,regular));
            return buscarCoincidencia();
        }
    }
    
    public boolean buscarCoincidencia() throws IOException {
        String intentoStr = intentos.get(0).getNumero();
        int intentoNum = Integer.parseInt(intentoStr);
        Intento intentoNuevo;
        boolean rtaEncontrada = false;
        
        while (!rtaEncontrada) {
            intentoNum++;

            if (intentoNum < 1000) {
                intentoStr = "0"+intentoNum;
            } else if (intentoNum > 9876) {
                intentoNum = 0123;
                intentoStr = "0123";
            } else {
                intentoStr = ""+intentoNum;
            }

            if (esValido(intentoStr)) {
                boolean coincide = true;
                for (Intento att : intentos) {
                    intentoNuevo = compararNumero(intentoStr, att.getNumero());

                    if (intentoNuevo.getBien() == att.getBien() && 
                        intentoNuevo.getRegular() == att.getRegular()) {
                    } else {
                        coincide = false;
                        break;
                    }
                }
                
                if (coincide) {
                    System.out.println("¿El número es "+intentoStr+"?");
                    System.out.println("Números bien:");
                    while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
                        System.out.println("Ingrese un número de 0 a 5:");
                        AdivinadorJuego.in.next();
                    }
                    int bien = AdivinadorJuego.in.nextInt();
                    System.out.println("Números regular:");
                    while (!AdivinadorJuego.in.hasNextInt(5) || !AdivinadorJuego.in.hasNext("\\d")) {
                        System.out.println("Ingrese un número de 0 a 5:");
                        AdivinadorJuego.in.next();
                    }
                    int regular = AdivinadorJuego.in.nextInt();

                    if (bien == 4) {
                        System.out.println("La respuesta es "+intentoStr);
                        return true;
                    } else {
                        intentos.add(new Intento (intentoStr, bien, regular));
                    }
                }
            }
        }
        
        
        return false;
    }
    
    public boolean esValido(String numero) {
        // 4 digitos
        if (!numero.matches("\\d\\d\\d\\d")) return false;
        
        // cada caracter es distinto
        char charArray[] = numero.toCharArray();
        Arrays.sort(charArray);

        for (int i = 0; i < charArray.length - 1; i++) {
            // Comparo cada caracter con el siguiente. Como están ordenados,
            // si hay caracteres iguales estarán juntos.
            if (charArray[i] == charArray[i+1]) return false;
        }
        
        return true;
    }

    public Intento compararNumero(String intentoNuevo, String intentoViejo) {
        int bien = 0;
        int regular = 0;
        
        for (int i = 0; i<4; i++) {
            if (intentoViejo.charAt(i) == intentoNuevo.charAt(i)) {
                bien++;
            } else {
                for (int j = 0; j<4; j++) {
                    if (i==j) continue;
                    if (intentoViejo.charAt(i) == intentoNuevo.charAt(j)) {
                        regular++;
                    }
                }
            }
        }
        return new Intento(intentoNuevo, bien, regular);
    }
}
