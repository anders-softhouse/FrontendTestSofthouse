package com.softhouse.platform.service

import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Family
import com.softhouse.platform.storage.Person
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class FamilyService(
    private val familyRepository: FamilyRepository,
    private val personRepository: PersonRepository
) {
    fun findFamilyByFamilyId(id: String, userId: String): Optional<Family> {
        return Optional.ofNullable(familyRepository.findByIdAndUserId(id.toLong(), userId))
    }

    fun findAll(userId: String): MutableList<Family> {
        return familyRepository.findByUserId(userId)
    }

    fun findAllFamilyMembersByFamilyId(familyId: String, userId: String): MutableList<Person> {
        val family = familyRepository.findByIdAndUserId(familyId.toLong(), userId)

        return family?.getFamilyMembers() ?: mutableListOf()
    }

    fun storeFamily(family: com.softhouse.platform.rest.Family, userId: String): Family {
        return familyRepository.save(
            Family(
                name = family.familyName,
                userId = userId
            )
        )
    }

    fun addPersonIntoFamily(familyBody: com.softhouse.platform.rest.Family, userId: String): Family {
        val family = findFamilyByIdOrException(familyBody.familyId, userId)
        val person = findPersonByIdOrException(familyBody.personId, userId)

        if (family.getFamilyMembers().contains(person)) {
            throw IllegalArgumentException("Family member personId '${person.id}' is already in the family")
        }

        family.addFamilyMember(person)

        return familyRepository.save(family)
    }

    fun removePersonFromFamily(familyId: String, personId: String, userId: String): Family {
        val family = findFamilyByIdOrException(familyId, userId)
        val person = findPersonByIdOrException(personId, userId)

        if (!family.getFamilyMembers().contains(person)) {
            throw IllegalArgumentException("Family member with personId '${person.id}' doesn't exist in the family [$family]")
        }

        family.removeFamilyMember(person)

        return familyRepository.save(family)
    }

    fun removeFamilyByFamilyId(familyId: String, userId: String) {
        val family = findFamilyByIdOrException(familyId, userId)

        familyRepository.delete(family)
    }

    private fun findFamilyByIdOrException(familyId: String, userId: String): Family {
        return familyRepository.findByIdAndUserId(familyId.toLong(), userId) ?: throw EntityNotFoundException("Family with family id [$familyId] not found")
    }

    private fun findPersonByIdOrException(personId: String, userId: String): Person {
        return personRepository.findByIdAndUserId(personId.toLong(), userId) ?: throw EntityNotFoundException("Person with person id [$personId] not found")
    }
}
