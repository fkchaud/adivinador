package adivinadorjuego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Adivinador {
    
    private ArrayList<Character> numeros = new ArrayList<>();
    private ArrayList<Intento> intentos = new ArrayList<>();

    public Adivinador() {
        numeros.add('0');
        numeros.add('1');
        numeros.add('2');
        numeros.add('3');
        numeros.add('4');
        numeros.add('5');
        numeros.add('6');
        numeros.add('7');
        numeros.add('8');
        numeros.add('9');
    }

    private String pensarNumero() {
        char charArray[] = new char[4];
        ArrayList<Character> num = (ArrayList) this.numeros.clone();
        
        for (int i=0; i<4; i++) {
            // Random entre 0 y 9. El máximo va bajando de a 1 a medida que elijo números
            int indice = (int) (Math.random() * (num.size()-1));
            // Pongo uno de los números en el array
            charArray[i] = num.get(indice);
            // Elimino el número que puse para no volverlo a elegir
            num.remove(indice);
        }
        
        // Devuelvo el Array como un String
        return new String(charArray);
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
            return buscarCoincidencia(intento);
        }
    }
    
    public boolean buscarCoincidencia(String intento) throws IOException {
        int intentoNum = Integer.parseInt(intento);
        String intentoStr;
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
                        System.out.println("La respuesta es "+intento);
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
