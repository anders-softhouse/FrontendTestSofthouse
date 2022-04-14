package com.softhouse.platform.service


import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Person
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityNotFoundException
import kotlin.test.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonServiceTest {

    @Autowired
    private lateinit var familyRepository: FamilyRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var personService: PersonService

    @BeforeEach
    fun clear() {
        personRepository.deleteAll()
        familyRepository.deleteAll()
    }

    @Test
    fun `Find existing person`() {
        val person = Person(100L, "", "Stafan", "Stale", "123", "Tegnergatan 37")
        personRepository.save(person)

        val createdPerson = personRepository.findAll()[0]
        val foundPerson = personService.findByPersonId(createdPerson.id.toString())
        assertNotNull(foundPerson)

        assertEquals(person.firstName, foundPerson.get().firstName)
    }

    @Test
    fun `Can't find non-existing person`() {
        val person = Person(2L, "", "Stafan", "Stale", "123", "Tegnergatan 37")

        val foundPerson = personService.findByPersonId(person.id.toString())
        assertFalse(foundPerson.isPresent)
    }

    @Test
    fun `Find 3 people in find all if three are in database`() {
        val person1 = Person(1L, "", "Stafan", "Stale", "123", "Tegnergatan 37")
        val person2 = Person(2L, "", "Stafan", "Stale", "123", "Tegnergatan 37")
        val person3 = Person(3L, "", "Stafan", "Stale", "123", "Tegnergatan 37")

        personRepository.save(person1)
        personRepository.save(person2)
        personRepository.save(person3)

        val people = personService.findAll()
        assertEquals(people.size, 3)

        assertTrue(people.any { it.id == 1L })
        assertTrue(people.any { it.id == 2L })
        assertTrue(people.any { it.id == 3L })
    }

    @Test
    fun `Find none people in find all if none are in database`() {
        val people = personRepository.findAll()

        assertEquals(people.size, 0)
    }

    @Test
    fun `Store person`() {
        val person = com.softhouse.platform.rest.Person()
        person.firstName = "firstName"
        person.lastName = "lastName"
        person.phoneNumber = "phoneNumber"
        person.address = "address"

        personService.storePerson(person)

        val foundPerson = personService.findAll()
        assertNotNull(foundPerson)
    }

    @Test
    fun `Store person with invalid family`() {
        val person = com.softhouse.platform.rest.Person()
        person.firstName = "firstName"
        person.lastName = "lastName"
        person.phoneNumber = "phoneNumber"
        person.address = "address"
        person.family = "1"

        personService.storePerson(person)

        val foundPerson = personService.findAll()
        assertNotNull(foundPerson)
    }

    @Test
    fun `Delete one person`() {
        val person = Person(1L, "", "Stafan", "Stale", "123", "Tegnergatan 37")
        personRepository.save(person)

        val createdPerson = personRepository.findAll()[0]
        val foundPerson = personService.findByPersonId(createdPerson.id.toString())
        assertTrue(foundPerson.isPresent)

        personService.removePersonByPersonId(createdPerson.id.toString())

        val deletedPerson = personRepository.findById(createdPerson.id!!.toLong())
        assertFalse(deletedPerson.isPresent)
    }

    @Test
    fun `Fail to delete non existing person`() {
        assertThrows<EntityNotFoundException> { personService.removePersonByPersonId("1") }
    }
}

