package pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto;

public record FilmDto(Integer filmId,
                      String title,
                      String language,
                      Integer rentalDuration,
                      Double rentalRate) {
}
