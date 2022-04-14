package com.softhouse.platform.service

import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Person
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val familyRepository: FamilyRepository
) {
    fun findByPersonId(id: String): Optional<Person> {
        return personRepository.findById(id.toLong())
    }

    fun findAll(): List<Person> {
        return personRepository.findAll()
    }

    fun storePerson(person: com.softhouse.platform.rest.Person): Person {
        val family = person.family.toLongOrNull()?.let { familyRepository.findByIdOrNull(it) }

        if (family != null) {
            val person = Person(
                createdAt = LocalDateTime.now().toLocalDate().toString(),
                firstName = person.firstName,
                lastName = person.lastName,
                phoneNumber = person.phoneNumber,
                address = person.address,
                family = family
            )

            family.addFamilyMember(person)

            familyRepository.save(family)

            return personRepository.save(person)
        } else {
            return personRepository.save(
                Person(
                    createdAt = LocalDateTime.now().toLocalDate().toString(),
                    firstName = person.firstName,
                    lastName = person.lastName,
                    phoneNumber = person.phoneNumber,
                    address = person.address
                )
            )
        }
    }

    fun removePersonByPersonId(personId: String) {
        val person = personRepository.findByIdOrNull(personId.toLong()) ?: throw EntityNotFoundException("Person with person id [$personId] not found")

        personRepository.delete(person)
    }
}
