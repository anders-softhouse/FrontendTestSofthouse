package com.softhouse.platform.service

import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Person
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val familyRepository: FamilyRepository
) {
    fun findByPersonId(id: String, userId: String): Optional<Person> {
        return Optional.ofNullable(personRepository.findByIdAndUserId(id.toLong(), userId))
    }

    fun findAll(userId: String): List<Person> {
        return personRepository.findByUserId(userId)
    }

    fun storePerson(person: com.softhouse.platform.rest.Person, userId: String): Person {
        val family = person.family.toLongOrNull()?.let { familyRepository.findByIdAndUserId(it, userId) }

        if (family != null) {
            val person = Person(
                createdAt = LocalDateTime.now().toLocalDate().toString(),
                userId = userId,
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
                    userId = userId,
                    firstName = person.firstName,
                    lastName = person.lastName,
                    phoneNumber = person.phoneNumber,
                    address = person.address
                )
            )
        }
    }

    fun removePersonByPersonId(personId: String, userId: String) {
        val person = personRepository.findByIdAndUserId(personId.toLong(), userId) ?: throw EntityNotFoundException("Person with person id [$personId] not found")

        personRepository.delete(person)
    }
}
