package com.softhouse.platform.rest

import com.softhouse.platform.exceptionHandler
import com.softhouse.platform.service.FamilyService
import com.softhouse.platform.storage.Person
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.*
import java.util.*
import javax.persistence.EntityNotFoundException

@CrossOrigin
@RestController
@RequestMapping("api/")
class FamilyController(private val familyService: FamilyService) {

    @GetMapping("family/{familyId}")
    fun getFamily(  @RequestHeader( defaultValue = "1", required = false ) user : String, @PathVariable @NotNull familyId: String): Optional<com.softhouse.platform.storage.Family> {
        return familyService.findFamilyByFamilyId(familyId)
    }

    @GetMapping("families")
    fun getFamilies( @RequestHeader( defaultValue = "1", required = false ) user : String ): MutableList<com.softhouse.platform.storage.Family> {
        return familyService.findAll()
    }

    @GetMapping("family/{familyId}/members")
    fun getFamilyAndMembers( @RequestHeader( defaultValue = "1", required = false ) user : String, @PathVariable @NotNull familyId: String): List<Person> {
        return familyService.findAllFamilyMembersByFamilyId(familyId)
    }

    @PostMapping("family")
    fun createFamily( @RequestHeader( defaultValue = "1", required = false ) user : String, @RequestBody family: Family): com.softhouse.platform.storage.Family {
        return familyService.storeFamily(family)
    }

    @PatchMapping("family/addPerson")
    fun addPersonToFamily( @RequestHeader( defaultValue = "1", required = false ) user : String, @RequestBody family: Family): com.softhouse.platform.storage.Family {
        try {
            return familyService.addPersonIntoFamily(family)
        } catch (exception: Exception) {
            throw exceptionHandler(exception)
        }
    }

    @PatchMapping("family/{familyId}/removePerson/{personId}")
    fun removeFromFamily( @RequestHeader( defaultValue = "1", required = false ) user : String, @PathVariable @NotNull familyId: String, @PathVariable @NotNull personId: String): com.softhouse.platform.storage.Family {
        try {
            return familyService.removePersonFromFamily(familyId, personId)
        } catch (exception: Exception) {
            throw exceptionHandler(exception)
        }
    }

    @DeleteMapping("family/{familyId}")
    fun removeFamily( @RequestHeader( defaultValue = "1", required = false ) user : String, @PathVariable @NotNull familyId: String) {
        try {
            return familyService.removeFamilyByFamilyId(familyId)
        } catch (exception: EntityNotFoundException) {
            throw exceptionHandler(exception)
        }
    }
}
