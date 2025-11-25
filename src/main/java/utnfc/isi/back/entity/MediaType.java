package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEDIA_TYPES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDIA_TYPE_ID", nullable = false)
    private Integer mediaTypeId;
    @Column(name = "NAME", length = 120)
    private String name;

    public MediaType(String name) {
        this.mediaTypeId = mediaTypeId;
    }

}
