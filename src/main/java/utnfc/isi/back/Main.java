package utnfc.isi.back;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import utnfc.isi.back.entity.*;
import utnfc.isi.back.service.*;

import javax.print.attribute.standard.Media;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Se necesita el archivo 'tracks.csv' en la raiz del proyecto o en el classpath, por simplicidad lo asumimos.
// También se asume que las FK (Album, MediaType, Genero) referencian IDs válidos ya cargados en la DB por el DDL
// Si el DDL solo crea la estructura, deberías cargar datos de ejemplo para las FK primero.

public class Main {

    private static final String CSV_FILE_PATH = "D:\\PROGRAMACION\\BackEnd\\recu-pre-enunciado\\src\\main\\resources\\tracks_nuevos.csv"; // Ajusta la ruta si es necesario

    public static void main(String[] args) {
        // 1. Inicializar JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiUnidad");
        EntityManager em = emf.createEntityManager();

        // 2. Inicializar Services
        TrackService trackService = new TrackService(em);
        ArtistaService artistaService = new ArtistaService(em);
        AlbumService albumService = new AlbumService(em, artistaService);
        InvoiceService invoiceService = new InvoiceService(em);

        MediaTypeService mediaTypeService = new MediaTypeService(em);
        GeneroService generoService = new GeneroService(em);

        final Map<Integer, Track> tracks = new HashMap<>();
        final Map<Integer, Album> albums = new HashMap<>();
        final Map<Integer, Artista> artistas = new HashMap<>();
        final Map<Integer, Invoice> invoices = new HashMap<>();

        final Map<Integer, MediaType> mediaTypes = new HashMap<>();
        final Map<Integer, Genero> generos = new HashMap<>();

        // 3. Cargar datos desde CSV
        try {
            em.getTransaction().begin();
            CSVParser parser = null;

            try {
                // Configurar CSVParser respetando comillas y separadores
                parser = new CSVParserBuilder()
                        .withSeparator(',')
                        .withQuoteChar('"')
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            CSVReader reader = new CSVReaderBuilder(new FileReader(CSV_FILE_PATH))
                    .withCSVParser(parser)
                    .withSkipLines(1) // Saltar encabezado
                    .build();

            List<String[]> filas = reader.readAll();



            for (String[] campos : filas) {

                // Validación mínima
                if (campos.length < 8) {
                    System.out.println("Linea con columnas insuficientes, se omite.");
                    continue;
                }

                // Validar campos obligatorios
                if (campos[0].isBlank() || campos[1].isBlank() || campos[7].isBlank()) {
                    System.out.println("Linea con datos clave faltantes, se omite.");
                    continue;
                }


                // === PARSEO DE DATOS ===
                // albumes y sus datos, playlists y sus datos o invoices y sus datos
                //Name,Album,MediaType,Genre,Composer,Milliseconds,Bytes,UnitPrice
                String name = campos[0].trim();
                String albumTitle = campos[1].trim();
                String mediaTypeName = campos[2].trim();
                String genreName = campos[3].trim();
                String artistName = campos[4].trim();

                MediaType mediaType = mediaTypeService.getOrCreate(mediaTypeName);
                Genero genero = generoService.getOrCreate(genreName);

                Album album = albumService.getOrCreate(albumTitle, artistName);
                Artista artista = artistaService.getOrCreate(artistName);

                Integer milliseconds = Integer.parseInt(campos[5].trim());
                Integer bytes = Integer.parseInt(campos[6].trim());
                BigDecimal unitPrice = new BigDecimal(campos[7].trim());

                mediaTypes.putIfAbsent(mediaType.getMediaTypeId(), mediaType);
                generos.putIfAbsent(genero.getGenreId(), genero);

                // Crear Track
                Track track = new Track();
                track.setName(name);
                track.setAlbum(album);
                track.setMediaType(mediaType);
                track.setGenre(genero);
                track.setComposer(artista.getName());
                track.setMilliseconds(milliseconds);
                track.setBytes(bytes);
                track.setUnitPrice(unitPrice);

                trackService.nuevo(track);
            }
            em.getTransaction().commit();

            System.out.println("Archivo leído: " + CSV_FILE_PATH);
            System.out.println("Filas encontradas: " + filas.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.out.println("\nRESULTADOS DE LA IMPORTACIÓN");

            // Tracks
            List<Track> tracksCargados = trackService.listarTodos();
            System.out.printf("\n========== Tracks cargados: ==========%n");
            System.out.printf("Total: %d%n", tracksCargados.size());

            // Generos
            List<Genero> generosCargados = generoService.listarTodos();
            System.out.printf("\n========== Generos cargados: ==========%n");
            System.out.printf("Total: %d%n", generosCargados.size());

            // MediaTypes
            List<MediaType> mediaTypesCargados = mediaTypeService.listarTodos();
            System.out.printf("\n========== MediaTypes cargados: ==========%n");
            System.out.printf("Total: %d%n", mediaTypesCargados.size());

            System.out.println("## 1. Top 5 Tracks con Más Bytes");
            List<Track> topBytesTracks = trackService.findTop5ByBytes();

            for (Track t : topBytesTracks) {
                System.out.printf("  - %s (%s) - Bytes: %d\n", t.getName(), t.getAlbum().getTitle(), t.getBytes());
            }

            System.out.println("\n## 2. Tracks con Precio por Unidad Mayor a $1.00");
            List<Track> expensiveTracks = trackService.findTracksByUnitPriceGreaterThan(1.00);

            for (Track t : expensiveTracks) {
                System.out.printf("  - %s (Precio: %.2f)\n", t.getName(), t.getUnitPrice());
            }

            em.close();
            emf.close();
        }
    }

}