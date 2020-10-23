package pl.piotron.animals.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Data
@Table(name = "images")
public class ImageStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "upload_url")
    private String uploadUrl;
    @Column(name = "delete_url")
    private String deleteUrl;
    @ManyToOne
    @JsonBackReference
    private Advertisement advertisement;
}
