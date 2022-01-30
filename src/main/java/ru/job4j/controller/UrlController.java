package ru.job4j.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Url;
import ru.job4j.model.UrlDTO;
import ru.job4j.service.AppService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/url")
public class UrlController {
    private final AppService service;

    public UrlController(final AppService service) {
        this.service = service;
    }

    @PostMapping("/convert")
    public ResponseEntity convert(@RequestBody Map<String, String> body) {
        String url = body.get("url");
        Optional<Url> urlEntity = service.findUrlByName(url);
        if (url == null || url.isEmpty() || urlEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid url value");
        }
        String shortcut = service.generateValue(5);
        service.save(Url.of(url, shortcut));
        return ResponseEntity.ok("code : " + shortcut);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity redirect(@PathVariable String code) throws URISyntaxException {
        var url = service.findUrlByShortcut(code);
        if (url.isPresent()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI(url.get().getName()));
            url.get().setCalls(url.get().getCalls() + 1);
            service.save(url.get());
            return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Url not found");
    }

    @GetMapping("/statistic")
    public List<UrlDTO> statistic() {
        return service.getAllUrlDTO();
    }
}
