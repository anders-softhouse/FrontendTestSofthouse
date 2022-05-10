package com.softhouse.platform.repository

import com.softhouse.platform.storage.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long> {
    fun findByIdAndUserId(id: Long, userId: String): Person?
    fun findByUserId(userId: String): MutableList<Person>
}
