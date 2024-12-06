package pe.edu.cibertec.spring_mvc_cl2_rmiriam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmCreateDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmDetailDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.dto.FilmDto;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.entity.Language;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.repository.LanguageRepository;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.service.MaintenanceService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/maintenance")  // Define la ruta base para todas las operaciones de mantenimiento
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;  // Servicio encargado de la lógica de negocio relacionada con las películas

    @Autowired
    LanguageRepository languageRepository;  // Repositorio para obtener los idiomas disponibles

    // Método para cargar la página principal de mantenimiento con todas las películas
    @GetMapping("/start")
    public String start(Model model) {
        List<FilmDto> films = maintenanceService.findAllFilms();  // Obtiene todas las películas
        model.addAttribute("films", films);  // Añade la lista de películas al modelo
        return "maintenance";  // Retorna la vista "maintenance" que mostrará la lista de películas
    }

    // Método para mostrar los detalles de una película específica
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findDetailById(id);  // Obtiene los detalles de la película por ID
        model.addAttribute("film", filmDetailDto);  // Añade los detalles de la película al modelo
        return "maintenance-detail";  // Retorna la vista "maintenance-detail" para mostrar los detalles de la película
    }

    // Método para cargar el formulario de edición de una película existente
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findDetailById(id);  // Obtiene los detalles de la película por ID
        model.addAttribute("film", filmDetailDto);  // Añade los detalles de la película al modelo
        return "maintenance-edit";  // Retorna la vista "maintenance-edit" con el formulario de edición
    }

    // Método para confirmar la edición de una película
    @PostMapping("/edit-confirm")
    public String edit(@ModelAttribute FilmDetailDto film, Model model) {
        maintenanceService.updateFilm(film);  // Llama al servicio para actualizar la película
        return "redirect:/maintenance/start";  // Redirige al inicio de la página de mantenimiento
    }

    // Método para eliminar una película por su ID
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        boolean isDeleted = maintenanceService.deleteFilmById(id);  // Elimina la película y retorna el estado
        if (isDeleted) {
            model.addAttribute("message", "Película eliminada correctamente.");  // Mensaje de éxito
        } else {
            model.addAttribute("error", "No se pudo eliminar la película.");  // Mensaje de error
        }
        return "redirect:/maintenance/start";  // Redirige al inicio de la página de mantenimiento
    }

    // Método para cargar el formulario de creación de una nueva película
    @GetMapping("/create")
    public String create(Model model) {
        List<Language> languages = languageRepository.findAll();  // Obtiene la lista de idiomas disponibles
        FilmCreateDto newFilm = new FilmCreateDto(null, "", "", null, null, null, null, null, null, null, null, null);  // Crea un objeto vacío para la nueva película
        model.addAttribute("film", newFilm);  // Añade el objeto vacío al modelo para el formulario
        model.addAttribute("languages", languages);  // Añade la lista de idiomas al modelo
        return "maintenance-create";  // Retorna la vista "maintenance-create" con el formulario de creación
    }

    // Método para confirmar la creación de una nueva película
    @PostMapping("/create-confirm")
    public String create(@ModelAttribute FilmCreateDto film, Model model) {
        maintenanceService.createFilm(film);  // Llama al servicio para crear la nueva película
        return "redirect:/maintenance/start";  // Redirige al inicio de la página de mantenimiento
    }
}
