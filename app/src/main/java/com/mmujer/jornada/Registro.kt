package com.mmujer.jornada

data class Registro (
    val provincia: Int,
    val municipio: Int,
    val distrito: String,
    val sector: String,
    val fecha: String,
    val coordinador: String,
    val editor: String,
    val hogares: Int,
    val mujeresH: Int,
    val hombresH: Int,
    val comerciales: Int,
    val impactadasC: Int,
    val educativos: Int,
    val impactadasE: Int,
    val otros: Int,
    val impactadasO: Int,
    val talleres: Int,
    val mujeresT: Int,
    val hombresT: Int,
    val totalG: Int
)