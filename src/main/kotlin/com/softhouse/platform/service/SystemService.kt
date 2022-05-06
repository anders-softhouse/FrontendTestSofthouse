package com.softhouse.platform.service

import com.softhouse.platform.getUserId
import com.softhouse.platform.repository.FamilyRepository
import com.softhouse.platform.repository.PersonRepository
import com.softhouse.platform.storage.Family
import com.softhouse.platform.storage.Person
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SystemService(
    private val personRepository: PersonRepository,
    private val familyRepository: FamilyRepository
){

    fun clearDbForUser( user : String) {
        // var families : MutableList<Family> = familyRepository.findAllByUserId( getUserId( user ) )
        // familyRepository.deleteAll( families )
    }


    fun initDbForUser( user : String ) {


    }

}