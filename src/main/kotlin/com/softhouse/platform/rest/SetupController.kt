package com.softhouse.platform.rest

import com.softhouse.platform.exceptionHandler
import com.softhouse.platform.service.PersonService
import com.softhouse.platform.service.SystemService
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityNotFoundException

@CrossOrigin
@RestController
@RequestMapping("api/")
class SetupController(private val systemService : SystemService) {

    @GetMapping("setup")
    fun setup( @RequestHeader("User") user : String ) {
        systemService.clearDbForUser( user )

        // Setup all sample data
        systemService.initDbForUser( user )
    }

}
