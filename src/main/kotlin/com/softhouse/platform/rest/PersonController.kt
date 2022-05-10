package com.softhouse.platform.rest

import com.softhouse.platform.exceptionHandler
import com.softhouse.platform.service.PersonService
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityNotFoundException

@CrossOrigin
@RestController
@RequestMapping("api/")
class PersonController(private val personService: PersonService) {

    @GetMapping("person/{personId}")
    fun getPersonById(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @PathVariable @NotNull personId: String
    ): Optional<com.softhouse.platform.storage.Person> {
        return personService.findByPersonId(personId, userId)
    }

    @GetMapping("persons")
    fun getPeople(
        @RequestHeader( defaultValue = "1", required = false) userId : String
    ): List<com.softhouse.platform.storage.Person> {
        return personService.findAll(userId)
    }

    @PostMapping("person")
    fun createPerson(
        @RequestHeader(defaultValue = "1", required = false) userId : String,
        @RequestBody person: Person
    ): com.softhouse.platform.storage.Person {
        return personService.storePerson(person, userId)
    }

    @DeleteMapping("person/{personId}")
    fun removePerson(
        @RequestHeader(defaultValue = "1", required = false) userId : String,
        @PathVariable @NotNull personId: String
    ) {
        try {
            return personService.removePersonByPersonId(personId, userId)
        } catch (exception: EntityNotFoundException) {
            throw exceptionHandler(exception)
        }
    }
}
