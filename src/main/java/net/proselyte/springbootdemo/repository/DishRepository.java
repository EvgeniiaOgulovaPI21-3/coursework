package net.proselyte.springbootdemo.repository;

import net.proselyte.springbootdemo.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 *  Интерфейс DishRepository представляет собой репозиторий для хранения данных о блюдах в MVC паттерне.
 *  @Query реализует метод пользовательского запроса на языке SQL.
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findById(Long id);
    @Query(value = "SELECT * FROM dish WHERE " +
            "CASE " +
            "  WHEN :keyword REGEXP '^[0-9]+$' THEN price <= CAST(:keyword AS DECIMAL(10,2)) " +
            "  ELSE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "END",
            nativeQuery = true)
    List<Dish> search(@Param("keyword") String keyword);
    List<Dish> findByPriceLessThanEqual(Integer price);

}
