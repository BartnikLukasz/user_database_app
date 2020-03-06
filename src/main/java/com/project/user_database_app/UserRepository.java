package com.project.user_database_app;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

    //Creating method that finds all Users whose last names match parameter
    Iterable<User> findByLastName(String lastName);

}
