package com.mmujer.jornada
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServicio {
    @POST("createRegistro")
    fun guardarDatos(@Body registro: Registro): Call<Registro>
}