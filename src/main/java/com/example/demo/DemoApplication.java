package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(CustomerRepository cc) {
        return event -> cc.findAll().forEach(System.out::println);
    }
}

@RestController
class CustomerHttpController {

    private final CustomerRepository repository;

    CustomerHttpController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    Iterable<Customer> byName(@RequestParam Optional<String> name) {
        System.out.println("by name? "   +
                           name.isPresent());
        var customers = name
                .map(this.repository::findByName)
                .orElse(this.repository.findAll());
        return customers;
    }

    @GetMapping("/customers/{id}")
    Customer byId(@PathVariable Integer id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("the id is null"));
    }

}


interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Iterable<Customer> findByName(String name);
}

record Customer(@Id Integer id, String name) {
}