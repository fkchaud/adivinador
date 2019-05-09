package adivinadorjuego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Adivinador {
    
    private ArrayList<Character> numeros = new ArrayList<>();
    private String intento = "";

    private int bien = 0;
    private int regular = 0;

    private int mejorPuntuacion = 0;
    
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
                numeros.remove(intento.charAt(i));
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
        boolean rtaEncontrada;
        
        if ((2*bien + 1*regular)>mejorPuntuacion) {
            mejorPuntuacion = 2*bien + 1*regular;
        } 
        
        char caracteres[];
        char charTemp;
        String futuroIntento;
        LinkedList<String> listaAbierta = new LinkedList<>();
                
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++) {
                if (i!=j) {
                    caracteres = intento.toCharArray();
                    charTemp = caracteres[j];
                    caracteres[j] = caracteres[i];
                    caracteres[i] = charTemp;
                    futuroIntento = new String(caracteres);
                    
                    if (!listaAbierta.contains(futuroIntento)) {
                        listaAbierta.add(futuroIntento);
                    }
                }
            }
        }
        
        int bienN;
        int regularN;
        int puntuacionN;
        
        do {
            //toma el primer item de la lista abierta y pregunta
            futuroIntento = listaAbierta.pollFirst();
            System.out.println("¿El número es "+futuroIntento+"?");
            System.out.println("Números bien:");
            bienN = Integer.parseInt(AdivinadorJuego.br.readLine());
            System.out.println("Números regular:");
            regularN = Integer.parseInt(AdivinadorJuego.br.readLine());
            puntuacionN = 2*bienN + 1*regularN;
        } while (puntuacionN <= mejorPuntuacion && !listaAbierta.isEmpty());
        
        if (bienN == 4) {
            rtaEncontrada = true;
        } else if (listaAbierta.isEmpty()) {
            rtaEncontrada = false;
        } else {
            bien = bienN;
            regular = regularN;
            mejorPuntuacion = puntuacionN;
            
            rtaEncontrada = moverNumeros();
        }
        
        return rtaEncontrada;
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
