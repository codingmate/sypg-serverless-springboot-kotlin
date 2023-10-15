package dev.sypg.app.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")

class ProfileController {
    @GetMapping("")
        //, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProfile(): List<String> {
        return listOf("profile1", "profile2")
    }
}