package de.novatec.blog.SpringBootActuatorScalingK8s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ApiController {

    @GetMapping("/requests")
    public String requests() {
        return "Check Prometehus";
    }
}
