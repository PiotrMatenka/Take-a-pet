package pl.piotron.animals.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @Lob
    private String description;
    private double price;
    @NotNull
    private String city;
    private LocalDateTime start;
    private LocalDateTime end;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany (mappedBy = "advertisement")
    private List<ImageStorage> images = new ArrayList<>();

}
