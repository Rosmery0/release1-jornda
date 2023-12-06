package com.mmujer.jornada

data class Municipio(
    val ID: Int,
    val Descripcion: String,
    val IDProvincia: Int,
    val IDMunicipioPadre: Int
)