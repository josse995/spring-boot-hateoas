package com.example.resthateoas.payroll;

import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinksHelper {
    public static List<Link> getAvailableLinks(Employee employee){
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel());
        links.add(linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
        if (employee.isActive()){
            links.add(linkTo(methodOn(EmployeeController.class).fireEmployee(employee.getId())).withRel("POST /fire"));
        }
        return links;
    }
}
