package com.app.usersservice.repository;

import com.app.usersservice.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest // ta adnotacja pozwala nam przygotowac sorodowisko testowe
// dla warstwy repozytoryjnej - co robi w tym celu:
// 1. automatycznie tworzy testowa baze danych oparta o H2
// 2. mapuje wszystkie modele i tworzy tabele w bazie danych
// 3. przygotuje specjalny bean TestEntityManager, ktory pozwala taki test
//    przeprowadzac
@TestPropertySource(locations = "classpath:application2.properties")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository personRepository;

    // dla kazdego testu tworzona jest osobna instancja bazy danych
    @Test
    void test1() {
        User u1 = User.builder().username("USER1").password("PWD1").build();
        User u2 = User.builder().username("USER2").password("PWD2").build();
        testEntityManager.persist(u1);
        testEntityManager.persist(u2);
        testEntityManager.flush();

        var people = personRepository.findAll();
        Assertions.assertThat(people).contains(u2);
        Assertions.assertThat(people).hasSize(2);
    }

}
