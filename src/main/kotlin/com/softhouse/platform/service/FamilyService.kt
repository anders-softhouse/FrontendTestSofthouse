package com.softhouse.platform.service

import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Family
import com.softhouse.platform.storage.Person
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class FamilyService(
    private val familyRepository: FamilyRepository,
    private val personRepository: PersonRepository
) {
    fun findFamilyByFamilyId(id: String): Optional<Family> {
        return familyRepository.findById(id.toLong())
    }

    fun findAll(): MutableList<Family> {
        return familyRepository.findAll()
    }

    fun findAllFamilyMembersByFamilyId(familyId: String): MutableList<Person> {
        val family = familyRepository.findByIdOrNull(familyId.toLong())

        return family?.getFamilyMembers() ?: mutableListOf()
    }

    fun storeFamily(family: com.softhouse.platform.rest.Family): Family {
        return familyRepository.save(
            Family(
                name = family.familyName
            )
        )
    }

    fun addPersonIntoFamily(familyBody: com.softhouse.platform.rest.Family): Family {
        val family = findFamilyByIdOrException(familyBody.familyId)
        val person = findPersonByIdOrException(familyBody.personId)

        if (family.getFamilyMembers().contains(person)) {
            throw IllegalArgumentException("Family member personId '${person.id}' is already in the family")
        }

        family.addFamilyMember(person)

        return familyRepository.save(family)
    }

    fun removePersonFromFamily(familyId: String, personId: String): Family {
        val family = findFamilyByIdOrException(familyId)
        val person = findPersonByIdOrException(personId)

        if (!family.getFamilyMembers().contains(person)) {
            throw IllegalArgumentException("Family member with personId '${person.id}' doesn't exist in the family [$family]")
        }

        family.removeFamilyMember(person)

        return familyRepository.save(family)
    }

    fun removeFamilyByFamilyId(familyId: String) {
        val family = findFamilyByIdOrException(familyId)

        familyRepository.delete(family)
    }

    private fun findFamilyByIdOrException(familyId: String): Family {
        return familyRepository.findByIdOrNull(familyId.toLong()) ?: throw EntityNotFoundException("Family with family id [$familyId] not found")
    }

    private fun findPersonByIdOrException(personId: String): Person {
        return personRepository.findByIdOrNull(personId.toLong()) ?: throw EntityNotFoundException("Person with person id [$personId] not found")
    }
}
