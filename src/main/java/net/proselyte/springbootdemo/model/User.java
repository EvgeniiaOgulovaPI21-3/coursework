package net.proselyte.springbootdemo.model;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Класс User - это модель виртуальной таблицы, которая хранит информацию об id, логине, пароле и роли пользователя.
 * @Data автоматически генерирует методы геттеров, сеттеров и прочее для всех полей класса;
 * @Entity указывает, что класс является сущностью базы данных;
 * @Table помечает класс в качестве сущности, которая соответствовует таблице user в базе данных;
 * @AllArgsConstructor автоматически генерирует конструктор, который принимает все поля класса в качестве аргументов;
 * @NoArgsConstructor - конструктор без аргументов;
 * @ToString возвращает строковое представление объекта;
 * @Id обозначает уникальный идентификатор;
 * @GeneratedValue автоматически генерирует ключ;
 * @Column определяет соответсвие для колонки в таблице user в базе данных.
 */
@Data
@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Column(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="login")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "Введите пароль")
    @Size(min = 6, message = "Пароль должен содержать не менее 6 символов")
    private String password;

    @Column(name = "role")
    private String role;

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

}
