package com.softhouse.platform.service

import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Family
import com.softhouse.platform.storage.Person
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityNotFoundException
import kotlin.test.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class FamilyServiceTest {

    @Autowired
    private lateinit var familyRepository: FamilyRepository
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var familyService: FamilyService

    @BeforeEach
    fun clear() {
        personRepository.deleteAll()
        familyRepository.deleteAll()
    }

    @Test
    fun `Find existing family`() {
        val family = Family(1L, "userId", "one")

        familyRepository.save(family)
        val foundFamily = familyService.findFamilyByFamilyId("1", "userId")
        assertNotNull(foundFamily)
    }

    @Test
    fun `Can't find non existing family`() {
        val family = Family(1L, "userId", "one")

        familyRepository.save(family)
        val foundFamily = familyService.findFamilyByFamilyId("1", "userId")
        assertNotNull(foundFamily)
    }

    @Test
    fun `Find all 3 defined families`() {
        val family1 = Family(1L, "userId","one")
        val family2 = Family(2L, "userId","two")
        val family3 = Family(3L, "userId","three")

        familyRepository.save(family1)
        familyRepository.save(family2)
        familyRepository.save(family3)

        val foundFamily = familyService.findAll("userId")
        assertTrue(foundFamily.size == 3)
    }

    @Test
    fun `Filter out families if incorrect userId`() {
        val family1 = Family(1L, "userId1", "one")
        val family2 = Family(2L, "userId2", "two")
        val family3 = Family(3L, "userId3", "three")

        familyRepository.save(family1)
        familyRepository.save(family2)
        familyRepository.save(family3)

        val foundFamily = familyService.findAll("userId1")
        assertTrue(foundFamily.size == 1)
    }

    @Test
    fun `Find no families if none are defined`() {
        val foundFamily = familyService.findAll("userId")
        assertTrue(foundFamily.size == 0)
    }

    @Test
    fun testStoreFamily() {
        val family = com.softhouse.platform.rest.Family()
        family.familyName = "familyName"
        family.familyId = "1"
        family.personId = "1"

        familyService.storeFamily(family, "userId")

        val foundFamily = familyService.findAll("userId")
        assertTrue(foundFamily.size == 1)
    }

    @Test
    fun testRemoveFamilyByFamilyId() {
        val family = Family(1L, "userId","one" )

        val createdFamily = familyRepository.save(family)

        familyService.removeFamilyByFamilyId(createdFamily.id.toString(), "userId")

        val foundFamily = familyService.findAll("userId")
        assertTrue(foundFamily.isEmpty())
    }

    @Test
    fun `Can't delete non existing family testRemoveFamilyByFamilyId`() {
        assertThrows<EntityNotFoundException> { familyService.removeFamilyByFamilyId("1", "userId") }
    }

    @Test
    fun `Update family with new family member`() {
        val family = Family(1L, "userId","one" )
        val person = Person(1L, "userId","", "Stafan", "one", "123", "Tegnergatan 37")

        val createdFamily = familyRepository.save(family)
        val createdPerson = personRepository.save(person)

        val familyBody = com.softhouse.platform.rest.Family()
        familyBody.familyName = createdFamily.name
        familyBody.familyId = createdFamily.id.toString()
        familyBody.personId = createdPerson.id.toString()

        familyService.addPersonIntoFamily(familyBody, "userId")

        val foundFamily = familyService.findFamilyByFamilyId(familyBody.familyId, "userId")
        assertEquals(foundFamily.get().getFamilyMembers()[0].id,  createdPerson.id)
    }

    @Test
    fun `Can't update non existing family addPersonIntoFamily`() {
        val family = Family(1L, "userId","one" )
        val person = Person(1L, "userId","", "Stafan", "one", "123", "Tegnergatan 37")

        val createdFamily = familyRepository.save(family)
        personRepository.save(person)

        val familyBody = com.softhouse.platform.rest.Family()
        familyBody.familyName = "Stafan"
        familyBody.familyId = createdFamily.id.toString()
        familyBody.personId = "1"

        assertThrows<EntityNotFoundException> { familyService.addPersonIntoFamily(familyBody, "userId") }
    }

    @Test
    fun `Update family by removing family member`() {
        val family = Family(1L, "userId","one" )
        val personToDelete = Person(1L, "userId","", "Stafan1", "one", "123", "Tegnergatan 37")
        val personToKeep = Person(1L, "userId","", "Stafan2", "one", "123", "Tegnergatan 37")

        val createdFamily = familyRepository.save(family)
        personToDelete.setFamily(createdFamily)
        personToKeep.setFamily(createdFamily)
        val createdPersonToDelete = personRepository.save(personToDelete)
        val createdPersonToKeep = personRepository.save(personToKeep)

        val foundFamily = familyService.findAllFamilyMembersByFamilyId(createdFamily.id.toString(), "userId")
        assertEquals(foundFamily.size, 2)

        familyService.removePersonFromFamily(createdFamily.id.toString(), createdPersonToDelete.id.toString(), "userId")

        val foundFamilyWithRemovedPerson = familyService.findAllFamilyMembersByFamilyId(createdFamily.id.toString(), "userId")
        assertEquals(foundFamilyWithRemovedPerson.size, 1)
        assertNotEquals(foundFamilyWithRemovedPerson[0].id, createdPersonToDelete.id)
        assertEquals(foundFamilyWithRemovedPerson[0].id, createdPersonToKeep.id)
    }

    @Test
    fun `Can't update non existing family removePersonFromFamily`() {
        val family = Family(1L, "userId","one" )
        val personToDelete = Person(1L, "userId","", "Stafan1", "one", "123", "Tegnergatan 37")
        val personToKeep = Person(1L, "userId","", "Stafan2", "one", "123", "Tegnergatan 37")

        val createdFamily = familyRepository.save(family)
        personToDelete.setFamily(createdFamily)
        personToKeep.setFamily(createdFamily)
        personRepository.save(personToDelete)
        personRepository.save(personToKeep)

        val foundFamily = familyService.findAllFamilyMembersByFamilyId(createdFamily.id.toString(), "userId")
        assertEquals(foundFamily.size, 2)

        assertThrows<EntityNotFoundException> { familyService.removePersonFromFamily(createdFamily.id.toString(), "1", "userId") }
    }
}
