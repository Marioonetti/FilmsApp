package com.example.appentretenimiento.domain.useclases.mezcla

import com.example.appentretenimiento.data.repositories.AllRepository
import javax.inject.Inject

class GetBySearch @Inject constructor(private val all: AllRepository) {

    fun invoke(nombre: String, pagina: Int) = all.getBySearch(nombre, pagina)

}