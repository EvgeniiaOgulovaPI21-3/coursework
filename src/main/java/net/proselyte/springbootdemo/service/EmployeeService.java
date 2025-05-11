package net.proselyte.springbootdemo.service;
import net.proselyte.springbootdemo.model.Employee;
import net.proselyte.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Класс EmployeeService связывает репозиторий EmployeeRepository и модель Employee.
 * @Autowired - аннотация для автоматического внедрения зависимости (в данном случае - EmployeeRepository);
 * findById(Long id) - метод для получения сущности Employee по ее идентификатору;
 * findAll() - метод для получения списка всех сущностей Employee;
 * saveEmployee(Employee employee) - метод для сохранения сущности Employee в базе данных;
 * deleteById(Long id) - метод для удаления сущности Employee из базы данных по ее идентификатору;
 * listAll(String keyword) - метод для получения списка всех сущностей Employee из базы данных, либо списка сущностей,
 * в имени которых содержится заданный ключевой слово (keyword).
 */
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(Long id){
        return employeeRepository.findById(id).get();
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteById(Long id){
        employeeRepository.deleteById(id);
    }

    public List<Employee> listAll(String keyword) {
        if (keyword != null) {
            return employeeRepository.search(keyword);
        } else {
            return employeeRepository.findAll();
        }
    }
}
