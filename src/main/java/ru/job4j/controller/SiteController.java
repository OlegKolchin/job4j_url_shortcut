package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.Person;
import ru.job4j.model.Site;
import ru.job4j.service.AppService;


@RestController
@RequestMapping("/site")
public class SiteController {

    private final AppService service;
    private BCryptPasswordEncoder encoder;

    public SiteController(AppService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> save(@RequestBody Site site) {
        if (service.findSiteByName(site.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("registration : false. Site already registered");
        }
        service.save(site);
        String login = service.generateValue(10);
        String password = service.generateValue(10);
        service.save(Person.of(login, encoder.encode(password), site));
        String body = String.format("registration : %s, login : %s, password : %s", "true", login, password);
        return ResponseEntity.ok().body(body);
    }
}
