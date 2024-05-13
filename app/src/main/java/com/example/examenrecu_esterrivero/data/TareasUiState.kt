package com.example.examenrecu_esterrivero.data

data class TareasUiState(
    var textoUltAccion: String = "No se ha contratado ninguna hora.",
    var textoResumen: String = "No se han contratado horas a ninguna tarea.",
    var textoTotalHoras: String = "",
    var nuevasTareas: ArrayList<Tarea> = ArrayList()
)
