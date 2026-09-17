package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ALBUMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALBUM_ID", nullable = false)
    private Integer albumId;

    @Column(name = "TITLE", nullable = false, length = 160)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artista artista;

}
