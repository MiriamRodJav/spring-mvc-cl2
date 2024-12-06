package pe.edu.cibertec.spring_mvc_cl2_rmiriam.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmCategoryId {

    private Integer filmId;
    private Integer categoryId;

}

