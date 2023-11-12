package dev.sypg.app.web.rest

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/project")
class ProjectController {

    data class Project(val name: String, val reactFilePath: String)
    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping("/", produces = ["application/json"])
    fun getProjectList(): List<Project> {
        println("hi?")
        return listOf(
            Project("음양력 전환", "calendar-converter"),
            Project("미개발", "")
        )
    }
}