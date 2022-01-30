package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Site;
import ru.job4j.model.Url;
import ru.job4j.model.Person;
import ru.job4j.model.UrlDTO;
import ru.job4j.repository.SiteRepository;
import ru.job4j.repository.UrlRepository;
import ru.job4j.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AppService {
    private final PersonRepository personRepository;
    private final SiteRepository siteRepository;
    private final UrlRepository urlRepository;

    public AppService(PersonRepository personRepository, SiteRepository siteRepository, UrlRepository urlRepository) {
        this.personRepository = personRepository;
        this.siteRepository = siteRepository;
        this.urlRepository = urlRepository;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Site save(Site site) {
        return siteRepository.save(site);
    }

    public Url save(Url url) {
        return urlRepository.save(url);
    }

    public List<Person> getAllPersons() {
        List<Person> people = new ArrayList<>();
        personRepository.findAll().forEach(people::add);
        return people;
    }

    public List<Site> getAllSites() {
        List<Site> sites = new ArrayList<>();
        siteRepository.findAll().forEach(sites::add);
        return sites;
    }

    public List<Url> getAllUrls() {
        List<Url> urls = new ArrayList<>();
        urlRepository.findAll().forEach(urls::add);
        return urls;
    }

    public List<UrlDTO> getAllUrlDTO() {
        return getAllUrls().stream().map(url -> UrlDTO.of(url.getName(), url.getCalls()))
                .collect(Collectors.toList());
    }

    public Optional<Person> findPersonById(int id) {
        return personRepository.findById(id);
    }

    public Optional<Site> findSiteById(int id) {
        return siteRepository.findById(id);
    }

    public Optional<Site> findSiteByName(String name) {
        return siteRepository.findSiteByName(name);
    }

    public Optional<Url> findUrlById(int id) {
        return urlRepository.findById(id);
    }

    public Optional<Url> findUrlByShortcut(String shortCut) {
        return urlRepository.findUrlByShortcut(shortCut);
    }

    public Optional<Url> findUrlByName(String name) {
        return urlRepository.findUrlByName(name);
    }

    public void deleteUrlById(int id) {
        urlRepository.deleteById(id);
    }

    public void deleteSiteById(int id) {
        siteRepository.deleteById(id);
    }

    public void deletePersonById(int id) {
        personRepository.deleteById(id);
    }

    public String generateValue(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = length;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
