package com.softhouse.platform.rest

import com.softhouse.platform.exceptionHandler
import com.softhouse.platform.service.FamilyService
import com.softhouse.platform.storage.Person
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityNotFoundException

@CrossOrigin
@RestController
@RequestMapping("api/")
class FamilyController(private val familyService: FamilyService) {

    @GetMapping("family/{familyId}")
    fun getFamily(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @PathVariable @NotNull familyId: String
    ): Optional<com.softhouse.platform.storage.Family> {
        return familyService.findFamilyByFamilyId(familyId, userId)
    }

    @GetMapping("families")
    fun getFamilies(
        @RequestHeader(defaultValue = "1", required = false) userId: String
    ): MutableList<com.softhouse.platform.storage.Family> {
        return familyService.findAll(userId)
    }

    @GetMapping("family/{familyId}/members")
    fun getFamilyAndMembers(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @PathVariable @NotNull familyId: String
    ): List<Person> {
        return familyService.findAllFamilyMembersByFamilyId(familyId, userId)
    }

    @PostMapping("family")
    fun createFamily(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @RequestBody family: Family
    ): com.softhouse.platform.storage.Family {
        return familyService.storeFamily(family, userId)
    }

    @PatchMapping("family/addPerson")
    fun addPersonToFamily(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @RequestBody family: Family
    ): com.softhouse.platform.storage.Family {
        try {
            return familyService.addPersonIntoFamily(family, userId)
        } catch (exception: Exception) {
            throw exceptionHandler(exception)
        }
    }

    @PatchMapping("family/{familyId}/removePerson/{personId}")
    fun removeFromFamily(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @PathVariable @NotNull familyId: String,
        @PathVariable @NotNull personId: String
    ): com.softhouse.platform.storage.Family {
        try {
            return familyService.removePersonFromFamily(familyId, personId, userId)
        } catch (exception: Exception) {
            throw exceptionHandler(exception)
        }
    }

    @DeleteMapping("family/{familyId}")
    fun removeFamily(
        @RequestHeader(defaultValue = "1", required = false) userId: String,
        @PathVariable @NotNull familyId: String
    ) {
        try {
            return familyService.removeFamilyByFamilyId(familyId, userId)
        } catch (exception: EntityNotFoundException) {
            throw exceptionHandler(exception)
        }
    }
}
