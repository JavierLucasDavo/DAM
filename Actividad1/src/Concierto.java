import java.io.*;

public class Concierto implements Serializable {
    private final String fecha;
    private final String lugar;
    private final String artista;
    private final String hora;

    public Concierto(String nombre, String lugar, String artista, String fecha) {
        this.artista = nombre;
        this.lugar = lugar;
        this.fecha = artista;
        this.hora = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public String getHora() {
        return hora;
    }

    public String getArtista() {
        return artista;
    }

    @Override
    public String toString() {
        return "Concierto{" +
                "artista='" + artista + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
