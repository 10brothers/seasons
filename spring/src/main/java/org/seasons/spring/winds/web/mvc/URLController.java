package org.seasons.spring.winds.web.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLController {

    @GetMapping("/")
    public String get() {
        return "\n";
    }

}
