package com.softhouse.platform.storage

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "User")
data class User(
    @Id
    @Column( name = "userId", updatable = false, nullable = false )
    val userId : Long? = null,

    val identifier : String
) {
    
}