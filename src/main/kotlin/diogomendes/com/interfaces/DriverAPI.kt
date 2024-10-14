package diogomendes.com.interfaces

import diogomendes.com.domain.Driver
import diogomendes.com.domain.DriverRepository
import diogomendes.com.domain.PatchDriver
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/drivers")
class DriverAPI(
    val driverRepository: DriverRepository
) {

    @GetMapping()
    fun listDrivers(): List<Driver> = driverRepository.findAll()

    @GetMapping("/{id}")
    fun findDriver(@PathVariable id: Long) =
        driverRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    @PostMapping
    fun createDriver(@RequestBody driver: Driver) = driverRepository.save(driver)

    @PatchMapping("/{id}")
    fun incrementalUpdateDriver(
        @PathVariable id: Long,
        @RequestBody driver: PatchDriver
    ): Driver {
        val foundDriver = findDriver(id)
        val copyDriver = foundDriver.copy(
            name = driver.name ?: foundDriver.name,
            birthDate = driver.birthDate ?: foundDriver.birthDate
        )
        return driverRepository.save(copyDriver)
    }

    @PutMapping("/{id}")
    fun fullUpdatedriver(
        @PathVariable id: Long,
        @RequestBody driver: Driver
    ): Driver {
        val foundDriver = findDriver(id)
        val copyDriver = foundDriver.copy(
            birthDate = driver.birthDate,
            name = driver.name
        )
        return driverRepository.save(copyDriver)
    }

    @DeleteMapping("/{id}")
    fun deleteDriver(@PathVariable id: Long) = driverRepository.deleteById(id)
}