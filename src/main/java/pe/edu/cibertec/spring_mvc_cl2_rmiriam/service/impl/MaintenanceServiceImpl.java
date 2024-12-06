package pe.edu.cibertec.spring_mvc_cl2_rmiriam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmCreateDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmDetailDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.entity.Film;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.entity.Language;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.repository.FilmRepository;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.repository.LanguageRepository;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.service.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service  // Indica que esta clase es un servicio de Spring
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;  // Repositorio para interactuar con la base de datos de películas

    @Autowired
    LanguageRepository languageRepository;  // Repositorio para interactuar con la base de datos de idiomas

    // Método que devuelve todas las películas como una lista de FilmDto
    @Override
    public List<FilmDto> findAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();  // Crea una lista para almacenar los DTOs de las películas
        Iterable<Film> iterable = filmRepository.findAll();  // Obtiene todas las películas desde el repositorio
        iterable.forEach(film -> {
            // Convierte cada objeto Film a un DTO FilmDto y lo añade a la lista
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });
        return films;  // Devuelve la lista de películas como DTOs
    }

    // Método que devuelve los detalles de una película por su ID
    @Override
    public FilmDetailDto findDetailById(Integer id) {

        Optional<Film> optional = filmRepository.findById(id);  // Busca la película por ID
        return optional.map(
                film -> new FilmDetailDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);  // Si no se encuentra la película, devuelve null
    }

    // Método para actualizar una película con los datos de FilmDetailDto
    @Override
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {

        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());  // Busca la película por ID
        return optional.map(
                film -> {
                    // Actualiza los detalles de la película con los valores del DTO
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);
    }

    // Método para eliminar una película por su ID
    @Override
    public Boolean deleteFilmById(Integer id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> {
                    filmRepository.delete(film);
                    return true;
                }
        ).orElse(false);
    }

    // Método para crear una nueva película a partir de FilmCreateDto
    @Override
    public FilmCreateDto createFilm(FilmCreateDto filmCreateDto) {
        Film film = new Film();  // Crea un nuevo objeto Film

        // Establece los detalles de la película usando los valores del DTO
        film.setTitle(filmCreateDto.title());
        film.setDescription(filmCreateDto.description());
        film.setReleaseYear(filmCreateDto.releaseYear());
        film.setRentalDuration(filmCreateDto.rentalDuration());
        film.setRentalRate(filmCreateDto.rentalRate());
        film.setLength(filmCreateDto.length());
        film.setReplacementCost(filmCreateDto.replacementCost());
        film.setRating(filmCreateDto.rating());
        film.setSpecialFeatures(filmCreateDto.specialFeatures());
        film.setLastUpdate(new Date());  // Establece la fecha de creación

        // Si el DTO tiene un ID de idioma, asigna el idioma correspondiente
        if (filmCreateDto.originalLanguageId() != null) {
            Language language = languageRepository.findById(filmCreateDto.originalLanguageId())
                    .orElseThrow(() -> new IllegalArgumentException("" + filmCreateDto.originalLanguageId()));  // Lanza una excepción si el idioma no se encuentra
            film.setLanguage(language);  // Asigna el idioma a la película
        }

        // Guarda la nueva película en el repositorio y la devuelve como un FilmCreateDto
        Film savedFilm = filmRepository.save(film);
        return new FilmCreateDto(
                savedFilm.getFilmId(),
                savedFilm.getTitle(),
                savedFilm.getDescription(),
                savedFilm.getReleaseYear(),
                savedFilm.getLanguage() != null ? savedFilm.getLanguage().getLanguageId() : null,  // Obtiene el ID del idioma si existe
                savedFilm.getRentalDuration(),
                savedFilm.getRentalRate(),
                savedFilm.getLength(),
                savedFilm.getReplacementCost(),
                savedFilm.getRating(),
                savedFilm.getSpecialFeatures(),
                savedFilm.getLastUpdate()
        );
    }
}
