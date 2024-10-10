import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String archivoCSV = "src/contenido/CONCIERTOS.csv";
        String archivoTXT = "src/contenido/concientos.txt";
        String archivoBIN = "src/contenido/concientos.bin";
        String archivoXML = "src/contenido/concientos.xml";
        String delimitador = ";";
        String linea;

        // Ej 1
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTXT))) {
            while ((linea = br.readLine()) != null) {
                bw.write(linea.toLowerCase());
                bw.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Ej 2
        /* try (RandomAccessFile fichero = new RandomAccessFile("src/contenido/concientos.aleatorios", "rw");
                BufferedReader br = new BufferedReader(new FileReader(archivoCSV))
                ) {
                while (( linea = br.readLine()) != null) {
                    String[] campos = linea.split(delimitador);
                    fichero.seek(0);
                    fichero.write(campos[0].getBytes());
                    fichero.seek(50);
                    fichero.write(campos[1].getBytes());
                    fichero.seek(100);
                    fichero.write(campos[2].getBytes());
                    fichero.seek(130);
                    fichero.write(campos[3].getBytes());
                    fichero.seek(140);
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } */

        //Ej 3
        ArrayList<Concierto> conciertos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV));
             ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivoBIN))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                Concierto concierto = new Concierto(fields[0], fields[1], fields[2], fields[3]);
                conciertos.add(concierto);
            }

            salida.writeObject(conciertos);
            System.out.println("Archivo CONCIERTOS.bin generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Ej 4
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivoBIN))) {
            ArrayList<Concierto> conciertos1 = (ArrayList<Concierto>) entrada.readObject();

            // Crear documento XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Elemento raíz
            Element rootElement = doc.createElement("conciertos");
            doc.appendChild(rootElement);

            // Añadir cada concierto
            for (Concierto concierto : conciertos) {
                Element conciertoElement = doc.createElement("concierto");

                Element artista = doc.createElement("artista");
                artista.appendChild(doc.createTextNode(concierto.getArtista()));
                conciertoElement.appendChild(artista);

                Element lugar = doc.createElement("lugar");
                lugar.appendChild(doc.createTextNode(concierto.getLugar()));
                conciertoElement.appendChild(lugar);

                Element fecha = doc.createElement("fecha");
                fecha.appendChild(doc.createTextNode(concierto.getFecha()));
                conciertoElement.appendChild(fecha);

                Element hora = doc.createElement("hora");
                hora.appendChild(doc.createTextNode(concierto.getHora()));
                conciertoElement.appendChild(hora);

                rootElement.appendChild(conciertoElement);
            }

            // Escribir el contenido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivoXML));
            transformer.transform(source, result);

            System.out.println("Archivo CONCIERTOS.xml generado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}