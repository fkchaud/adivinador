package adivinadorjuego;

public class Intento {
    private String numero;
    private int bien;
    private int regular;

    public Intento(String numero, int bien, int regular) {
        this.numero = numero;
        this.bien = bien;
        this.regular = regular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getBien() {
        return bien;
    }

    public void setBien(int bien) {
        this.bien = bien;
    }

    public int getRegular() {
        return regular;
    }

    public void setRegular(int regular) {
        this.regular = regular;
    }
    
    
}