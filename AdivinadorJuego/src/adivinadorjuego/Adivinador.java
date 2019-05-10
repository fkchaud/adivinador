package adivinadorjuego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Adivinador {
    
    private ArrayList<Character> numeros = new ArrayList<>();
    private String intento = "";

    private int bien = 0;
    private int regular = 0;

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
        boolean rtaEncontrada;
        
        intento = pensarNumero();
        System.out.println("¿El número es "+intento+"?");
        System.out.println("Números bien:");
        bien = Integer.parseInt(AdivinadorJuego.br.readLine());
        System.out.println("Números regular:");
        regular = Integer.parseInt(AdivinadorJuego.br.readLine());

        if (bien == 4) {
            rtaEncontrada = true;
        } else if (bien+regular == 0) {
            for (int i=0; i<4; i++) {
                numeros.remove((Character) intento.charAt(i));
            }
            rtaEncontrada = adivinar();
        } else if (bien+regular == 4) {
            rtaEncontrada = moverNumeros();
        } else {
            rtaEncontrada = cambiarUnNumero();
        }
        return rtaEncontrada;
    }

    private boolean moverNumeros() throws IOException {
        
        LinkedList<String> opciones = new LinkedList<>();
        
        // genero todas las opciones
        
        // la inicial
        opciones.add(intento);
        
        // las permutaciones
        char caracteresIntento[] = intento.toCharArray();
        String opcionArmada = "";
        
        for (int i=0; i<4; i++) {
            opcionArmada += caracteresIntento[i];
            for (int j=0; j<4; j++) {
                if (j==i) continue;
                opcionArmada += caracteresIntento[j];
                for (int k=0; k<4; k++) {
                    if (k==i || k==j) continue;
                    opcionArmada += caracteresIntento[k];
                    for (int l=0; l<4; l++) {
                        if (l==i || l==j || l==k) continue;
                        opcionArmada += caracteresIntento[l];
                        if (!opciones.contains(opcionArmada)) opciones.add(opcionArmada);
                        opcionArmada = opcionArmada.substring(0, opcionArmada.length()-1);
                    }
                    opcionArmada = opcionArmada.substring(0, opcionArmada.length()-1);
                }
                opcionArmada = opcionArmada.substring(0, opcionArmada.length()-1);
            }
            opcionArmada = opcionArmada.substring(0, opcionArmada.length()-1);
        }
        // fin de carga de permutaciones
        
        // elimino "intento" porque ya se probó antes (pero era necesario para no
        //duplicarla en las permutaciones)
        opciones.remove(intento);
        
        // si el intento no tenia ningun caracter bien ubicado, se pueden eliminar
        //todas las opciones que tienen un caracter en esa ubicacion
        if (bien == 0) opciones = limpiarOpciones(opciones, intento);
        
        // pruebo todas las opciones, una por una, hasta que alguna de bien=4 o bien=0
        String nuevoIntento;
        int bienN;
        do {
            nuevoIntento = opciones.pollFirst();
            System.out.println("¿El número es "+nuevoIntento+"?");
            System.out.println("Números bien:");
            bienN = Integer.parseInt(AdivinadorJuego.br.readLine());
            
            if (bienN == 0) {
                opciones = limpiarOpciones(opciones, nuevoIntento);
            }
        } while (bienN != 4);
        
        if (bienN == 4) {
            intento = nuevoIntento;
            return true;
        }
        
        return false;
    }
    
    private LinkedList<String> limpiarOpciones(LinkedList<String> opciones, String nuevoIntento) {
        LinkedList<String> opciones2 = new LinkedList<>();
        
        for (String opcion : opciones) {
            if (opcion.charAt(0) != nuevoIntento.charAt(0) && 
                opcion.charAt(1) != nuevoIntento.charAt(1) &&
                opcion.charAt(2) != nuevoIntento.charAt(2) &&
                opcion.charAt(3) != nuevoIntento.charAt(3)) 
                    opciones2.add(opcion);
        }
        return opciones2;
    }

    private boolean cambiarUnNumero() throws IOException {
        char caracteres[];
        String futuroIntento;
        int bienN;
        int regularN;
        
        for (int i=0; i<4; i++) {
            for (int j=0; j < numeros.size(); j++) {
                caracteres = intento.toCharArray();
                if (!intento.contains(""+numeros.get(j))) {
                    caracteres[i] = numeros.get(j);
                    futuroIntento = new String(caracteres);
                    
                    System.out.println("¿El número es "+futuroIntento+"?");
                    System.out.println("Números bien:");
                    bienN = Integer.parseInt(AdivinadorJuego.br.readLine());
                    System.out.println("Números regular:");
                    regularN = Integer.parseInt(AdivinadorJuego.br.readLine());

                    if ( (bienN+regularN) < (bien+regular) ) break;

                    if ( (bienN+regularN) > (bien+regular) ) {
                        intento = futuroIntento;
                        bien = bienN;
                        regular = regularN;
                        break;
                    }
                }
            }
            if (bien == 4) {
                return true;
            } else if (bien+regular == 4) {
                return moverNumeros();
            }
        }
        
        return cambiarUnNumero();
    }

    public String getIntento() {
        return intento;
    }
}
