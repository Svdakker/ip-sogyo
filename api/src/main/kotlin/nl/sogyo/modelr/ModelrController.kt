package nl.sogyo.modelr

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ModelrController {

    @PostMapping("/modelr/api/setup")
    fun setup(): Unit {

    }

    @PostMapping("modelr/api/run")
    fun run(): Unit {

    }
}
