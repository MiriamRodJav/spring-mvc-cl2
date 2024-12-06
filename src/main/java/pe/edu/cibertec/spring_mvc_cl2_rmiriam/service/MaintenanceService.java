package pe.edu.cibertec.spring_mvc_cl2_rmiriam.service;

import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmCreateDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmDetailDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();

    FilmDetailDto findDetailById(Integer id);

    Boolean updateFilm(FilmDetailDto filmDetailDto);

    Boolean deleteFilmById(Integer id);

    FilmCreateDto createFilm(FilmCreateDto filmCreateDto);

}
