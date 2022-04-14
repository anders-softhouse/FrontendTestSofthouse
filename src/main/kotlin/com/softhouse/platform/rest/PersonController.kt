package com.softhouse.platform.rest

import com.softhouse.platform.exceptionHandler
import com.softhouse.platform.service.PersonService
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("api/")
class PersonController(private val personService: PersonService) {

    @GetMapping("person/{personId}")
    fun getPersonById(@PathVariable @NotNull personId: String): Optional<com.softhouse.platform.storage.Person> {
        return personService.findByPersonId(personId)
    }

    @GetMapping("persons")
    fun getPeople(): List<com.softhouse.platform.storage.Person> {
        return personService.findAll()
    }

    @PostMapping("person")
    fun createPerson(@RequestBody person: Person): com.softhouse.platform.storage.Person {
        return personService.storePerson(person)
    }

    @DeleteMapping("person/{personId}")
    fun removePerson(@PathVariable @NotNull personId: String) {
        try {
            return personService.removePersonByPersonId(personId)
        } catch (exception: EntityNotFoundException) {
            throw exceptionHandler(exception)
        }
    }
}
