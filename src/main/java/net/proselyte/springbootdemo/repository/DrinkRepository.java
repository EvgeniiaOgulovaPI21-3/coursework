package net.proselyte.springbootdemo.repository;
import net.proselyte.springbootdemo.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 *  Интерфейс DrinkRepository представляет собой репозиторий для хранения данных о напитках в MVC паттерне.
 *  @Query реализует метод пользовательского запроса на языке SQL.
 */
@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findById(Long id);
    @Query(value = "SELECT * FROM drink WHERE " +
            "CASE " +
            "  WHEN :keyword REGEXP '^[0-9]+$' THEN price <= CAST(:keyword AS DECIMAL(10,2)) " +
            "  ELSE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "END",
            nativeQuery = true)
    List<Drink> search(@Param("keyword") String keyword);
    List<Drink> findByPriceLessThanEqual(Integer price);

}
