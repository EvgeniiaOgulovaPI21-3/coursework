package net.proselyte.springbootdemo.controller;

import net.proselyte.springbootdemo.model.Employee;
import net.proselyte.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
/**
 *  Класс EmployeeController управляет данными в архитектуре паттерна Model-View-Controller;
 *  связывает модель Employee и виртуальную таблицу, гарантируя передачу информации между ними;
 *  получает данные из виртуальной таблицы, обрабатывает и возвращает их;
 *  обрабатывает запросы от клиента, выполняет необходимые операции и возвращает результат.
 */
@Controller
public class EmployeeController {
    @ModelAttribute
    public void interceptor(Authentication authentication, Model model) {
        if (authentication == null) {
            model.addAttribute("authorized",false);
            model.addAttribute("isAdmin",false);
        }
        else {
            if (!authentication.getAuthorities().stream().map(d -> d.getAuthority()).toList().contains("USER")){
                model.addAttribute("isAdmin",true);}
            else {
                model.addAttribute("isAdmin",false);
            }
            model.addAttribute("authorized",true);
        }
    }
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String findByKey(Model model, @Param("keyword") String keyword){
        List<Employee> employee = employeeService.listAll(keyword);
        model.addAttribute("employee",employee);
        model.addAttribute("keyword",keyword);
        return "employee-list";
    }
    @GetMapping("/employee-create")
    public String createEmployeeForm(Employee employee){
        return "employee-create";
    }
    @PostMapping("/employee-create")
    public String createEmployee(Employee employee){
        employeeService.saveEmployee(employee);
        return "redirect:/employee";
    }
    @GetMapping("employee-delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteById(id);
        return "redirect:/employee";
    }
    @GetMapping("employee-update/{id}")
    public String updateEmployeeForm(@PathVariable("id") Long id,Model model){
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee",employee);
        return "/employee-update";
    }
    @PostMapping("/employee-update")
    public String updateEmployee(Employee employee){
        employeeService.saveEmployee(employee);
        return "redirect:/employee";
    }
}
