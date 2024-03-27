package com.ashleykoh.promojioserver;

import com.ashleykoh.promojioserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        repository.deleteAll();
//
//        // save a couple of customers
//        repository.save(new User("Alice", "Smith"));
//        repository.save(new User("Bob", "Smith"));
//
//        System.out.println("Users found with findAll():");
//        System.out.println("-------------------------------");
//        for (User user: repository.findAll()) {
//            System.out.println(user);
//        }
//        System.out.println();
//
//        System.out.println("Customer found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(repository.findUserByFirstName("Alice"));
//
//        System.out.println("Customers found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (User user : repository.findUsersByLastName("Smith")) {
//            System.out.println(user);
//        }
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String [] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//        };

}
