package com.softhouse.platform.storage

import lombok.Getter
import lombok.Setter
import javax.persistence.*

@Entity
@Table(name = "Person")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "User_Id", updatable = false, nullable = false)
    val userId: String,

    @Column(name = "CreatedAt", updatable = true, nullable = false)
    var createdAt: String,

    @Column(name = "FirstName", updatable = false, nullable = false)
    val firstName: String,

    @Column(name = "LastName", updatable = true, nullable = false)
    val lastName: String,

    @Column(name = "PhoneNumber", updatable = false, nullable = false)
    val phoneNumber: String,

    @Column(name = "Address", updatable = true, nullable = false)
    val address: String,

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "Family_Id")
    private var family: Family? = null
) {
    constructor() : this(null, "", "", "", "", "", "", null)

    fun setFamily(family: Family) {
        this.family = family
    }

    fun removeFromFamily(family: Family) {
        if (this.family?.id == family.id) {
            this.family = null
        }
    }
}
