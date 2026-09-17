package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PLAYLIST_TRACK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYLIST_TRACK_ID", nullable = false)
    private Integer playlistTrackId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "PLAYLIST_ID", nullable = false)
    private Playlist playlists;
    @ManyToOne(optional = false)
    @JoinColumn(name = "TRACK_ID", nullable = false)
    private Track tracks;
}
