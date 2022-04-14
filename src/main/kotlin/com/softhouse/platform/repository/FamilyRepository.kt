package com.softhouse.platform.repository

import com.softhouse.platform.storage.Family
import org.springframework.data.jpa.repository.JpaRepository

interface FamilyRepository: JpaRepository<Family, Long> {

}
