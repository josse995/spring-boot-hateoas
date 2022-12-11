package com.example.resthateoas.payroll;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
public class Employee extends RepresentationModel<Employee> {

    private final float yearBonusBase = 1000;
    private final float yearBonusByDayFromEnterDate = 0.02f;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String role;

    private boolean isActive;

    private Date enterDate;

    private Date endDate;

    public Employee() {}

    public Employee(String name, String role, boolean isActive, Date enterDate, Date endDate) {
        this.name = name;
        this.role = role;
        this.isActive = isActive;
        this.enterDate = enterDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return id == employee.id && isActive == employee.isActive && Objects.equals(name, employee.name) && Objects.equals(role, employee.role) && Objects.equals(enterDate, employee.enterDate) && Objects.equals(endDate, employee.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, role, isActive, enterDate, endDate);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                ", enterDate=" + enterDate +
                ", endDate=" + endDate +
                '}';
    }
}
