package pe.edu.cibertec.spring_mvc_cl2_rmiriam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_mvc_cl2_rmiriam.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
