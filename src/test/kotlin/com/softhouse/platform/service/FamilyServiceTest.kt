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
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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
        val family = Family(1L, "one")

        familyRepository.save(family)
        val foundFamily = familyService.findFamilyByFamilyId("1")
        assertNotNull(foundFamily)
    }

    @Test
    fun `Can't find non existing family`() {
        val family = Family(1L, "one")

        familyRepository.save(family)
        val foundFamily = familyService.findFamilyByFamilyId("1")
        assertNotNull(foundFamily)
    }

    @Test
    fun `Find all 3 defined families`() {
        val family1 = Family(1L, "one")
        val family2 = Family(2L, "two")
        val family3 = Family(3L, "three")

        familyRepository.save(family1)
        familyRepository.save(family2)
        familyRepository.save(family3)

        val foundFamily = familyService.findAll()
        assertTrue(foundFamily.size == 3)
    }

    @Test
    fun `Find no families if none are defined`() {
        val foundFamily = familyService.findAll()
        assertTrue(foundFamily.size == 0)
    }

    @Test
    fun testStoreFamily() {
        val family = com.softhouse.platform.rest.Family()
        family.familyName = "familyName"
        family.familyId = "1"
        family.personId = "1"

        familyService.storeFamily(family)

        val foundFamily = familyService.findAll()
        assertTrue(foundFamily.size == 1)
    }

    @Test
    fun testRemoveFamilyByFamilyId() {
        val family = Family(1L, "one")

        val createdFamily = familyRepository.save(family)

        familyService.removeFamilyByFamilyId(createdFamily.id.toString())

        val foundFamily3 = familyService.findAll()
        assertTrue(foundFamily3.isEmpty())
    }

    @Test
    fun `Can't delete non existing family testRemoveFamilyByFamilyId`() {
        assertThrows<EntityNotFoundException> { familyService.removeFamilyByFamilyId("1") }
    }
}
