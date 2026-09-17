package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "TRACKS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACK_ID", nullable = false)
    private Integer trackId;
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;
    @ManyToOne(optional = true) // (algunos tracks pueden no tener album).
    @JoinColumn(name = "ALBUM_ID")
    private Album album;
    @ManyToOne(optional = false)
    @JoinColumn(name = "MEDIA_TYPE_ID", nullable = false)
    private MediaType mediaType;
    @ManyToOne(optional = true)
    @JoinColumn(name = "GENRE_ID")
    private Genero genre;
    @Column(name = "COMPOSER", length = 220)
    private String composer;
    @Column(name = "MILLISECONDS", nullable = false)
    private Integer milliseconds;
    @Column(name = "BYTES")
    private Integer bytes;
    @Column(name = "UNIT_PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public double getDurationInMinutes() {
        return milliseconds != null ? milliseconds / 60000.0 : 0.0;
    }
    public boolean hasValidPrice() {
        return unitPrice != null && unitPrice.doubleValue() > 0.0;
    }
}
