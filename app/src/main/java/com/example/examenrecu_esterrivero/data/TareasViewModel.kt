package com.example.examenrecu_esterrivero.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class TareasViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(TareasUiState())
    val uiState: StateFlow<TareasUiState> = _uiState.asStateFlow()

    var nombreTarea by mutableStateOf("")

    fun TareaNueva (nuevoNombreTarea: String){
        nombreTarea = nuevoNombreTarea
    }

    fun AnnadirHora (tareaTipo: Tarea, tareas: ArrayList<Tarea>){
        val hora = 1
        var totalHoras = 0
        var textoUltAccionAct = ""
        var textoResumenAct = ""
        var textoTotalHorasAct = ""

        for(tarea in tareas){
            if(tarea.nombre == tareaTipo.nombre){
                tarea.recuentoHoras += hora
                textoUltAccionAct = "Se añade $hora hora a ${tarea.nombre}"
            }
            if(tarea.recuentoHoras > 0){
                textoResumenAct += "La tarea: ${tarea.nombre} Precio: ${tarea.precio} Horas: ${tarea.recuentoHoras}\n"
            }
            totalHoras += tarea.recuentoHoras
            textoTotalHorasAct = "Total horas: $totalHoras"
        }

        _uiState.update {
            actualizarTexto -> actualizarTexto.copy(
                textoUltAccion = textoUltAccionAct,
                textoResumen = textoResumenAct,
                textoTotalHoras = textoTotalHorasAct
            )
        }

    }

    fun AnnadirNuevaTarea(tareas: ArrayList<Tarea>, nombreNuevaTarea: String){

        var existe = false
        var tareaNueva = Tarea("", 0, 0)
        var textoUltAccionAct = ""
        var precioNuevo = Random.nextInt(1,15)

        for(tarea in tareas){
            if("".equals(nombreNuevaTarea)) {
                existe = true
                textoUltAccionAct = "El valor es vacío o en blanco."
            }else if(tarea.nombre.equals(nombreNuevaTarea, ignoreCase = true)){
                existe = true
                textoUltAccionAct = "Ya existe una tarea llamada así."
            }
        }

        if(!existe){
            tareaNueva = Tarea(nombreNuevaTarea, precioNuevo,0)
            tareas.add(tareaNueva)
            textoUltAccionAct = "Añadida tarea ${tareaNueva.nombre}"
        }

        _uiState.update {
            actualizarTexto -> actualizarTexto.copy(
                textoUltAccion = textoUltAccionAct,
                nuevasTareas = tareas

            )
        }
    }

}