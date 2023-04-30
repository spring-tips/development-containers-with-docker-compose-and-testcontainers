package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    CustomerHttpController(
            CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers/{name}")
    Iterable<Customer> byName(@PathVariable String name) {
        return this.repository.findByName(name);
    }

    @GetMapping("/customers")
    Iterable<Customer> customers() {
        return this.repository.findAll();
    }
}


interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Iterable<Customer> findByName(String name);
}

record Customer(@Id Integer id, String name) {
}