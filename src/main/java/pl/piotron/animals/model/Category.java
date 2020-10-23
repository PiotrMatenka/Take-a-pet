package pl.piotron.animals.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Category {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @NotNull
 private String name;
 @Column(name = "image_url")
 private String imageUrl;
 @OneToMany(mappedBy = "category")
 @JsonBackReference
 private Set<Advertisement> advertisements = new HashSet<>();


}
