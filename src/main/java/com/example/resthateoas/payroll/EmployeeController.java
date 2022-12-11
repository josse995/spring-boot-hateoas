package com.example.resthateoas.payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll(){
        List<EntityModel<Employee>> employees = repository.findAll().stream().map(employee ->
                EntityModel.of(employee, LinksHelper.getAvailableLinks(employee))
        ).toList();
        return ResponseEntity.ok(CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel()));
    }

    @PostMapping("/")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee){
        try{
            Employee employee = repository.save(newEmployee);

            EntityModel<Employee> employeeEntityModel = EntityModel.of(employee, LinksHelper.getAvailableLinks(employee));
            return ResponseEntity.created(new URI(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(employeeEntityModel);
        }catch(URISyntaxException e){
            return ResponseEntity.badRequest().body("Unable to create " + newEmployee);
        }
    }

    //Single item

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Employee>> findOne(@PathVariable long id){
        return repository.findById(id).map(employee -> EntityModel.of(employee, LinksHelper.getAvailableLinks(employee)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/fire")
    ResponseEntity<?> fireEmployee(@PathVariable long id) {
        return repository.findById(id).map(employee -> {
            employee.setEndDate(new Date());
            employee.setActive(false);
            Employee savedEmployee = repository.save(employee);
            EntityModel<Employee> employeeEntityModel = EntityModel.of(savedEmployee, LinksHelper.getAvailableLinks(savedEmployee));
                    try {
                        return ResponseEntity.created(new URI(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                                .body(employeeEntityModel);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.badRequest().body("Unable to fire employee with id: " + id);
                    }
                })
        .orElse(ResponseEntity.notFound().build());
    }
}
