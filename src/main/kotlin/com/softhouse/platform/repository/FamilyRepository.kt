package com.softhouse.platform.repository

import com.softhouse.platform.storage.Family
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FamilyRepository: JpaRepository<Family, Long> {
    fun findByIdAndUserId(id: Long, userId: String): Family?
    fun findByUserId(userId: String): MutableList<Family>
}
