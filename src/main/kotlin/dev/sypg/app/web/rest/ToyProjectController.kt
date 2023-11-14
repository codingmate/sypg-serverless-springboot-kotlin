package dev.sypg.app.web.rest

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/toy-project")
@CrossOrigin(origins = ["http://localhost:3000", "http://sypg-react.s3-website.ap-northeast-2.amazonaws.com"])
class ToyProjectController {

    data class Project(val name: String, val reactFilePath: String)
    @GetMapping("", produces = ["application/json"])
    fun getProjectList(): List<Project> {

        return listOf(
            Project("음양력 전환", "calendar-converter"),
            Project("미개발", "")
        )
    }
}