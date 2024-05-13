package com.example.examenrecu_esterrivero

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenrecu_esterrivero.data.DataSource
import com.example.examenrecu_esterrivero.data.Tarea
import com.example.examenrecu_esterrivero.data.TareasUiState
import com.example.examenrecu_esterrivero.data.TareasViewModel
import com.example.examenrecu_esterrivero.ui.theme.ExamenRecu_EsterRiveroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenRecu_EsterRiveroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(tareas = DataSource.tareas, viewModelTareas = viewModel())
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(tareas: ArrayList<Tarea>, viewModelTareas: TareasViewModel) {

    val uiState by viewModelTareas.uiState.collectAsState()
    uiState.nuevasTareas = tareas

    Column {
        PantallaPrincipalTareas(tareas =tareas , viewModelTareas = viewModelTareas)
        PantallaTextEditorYBoton(viewModelTareas = viewModelTareas, tareas = tareas)
        PantallaTextosInferiores(uiState = uiState)
    }

}

@Composable
fun PantallaPrincipalTareas(modifier : Modifier = Modifier, tareas: ArrayList<Tarea>, viewModelTareas: TareasViewModel){

    Column (modifier) {
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.height(400.dp)){
            items(tareas){tarea->
                Card(
                    modifier
                        .height(200.dp)
                        .padding(5.dp)) {
                    Text(text = "Tarea: ${tarea.nombre}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp))
                    Text(text = "€/hora: ${tarea.precio}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp))
                    Button(onClick = { viewModelTareas.AnnadirHora(tarea, tareas) },
                        modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = "+")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTextEditorYBoton(viewModelTareas: TareasViewModel, tareas: ArrayList<Tarea>){
    Row (modifier = Modifier.padding(10.dp)) {
        TextField(value = viewModelTareas.nombreTarea,
            onValueChange = { viewModelTareas.TareaNueva(it) },
            label = { Text(text = "Nombre nueva tarea")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
        Button(onClick = { viewModelTareas.AnnadirNuevaTarea(tareas, viewModelTareas.nombreTarea) },
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)) {
            Text(text = "Nueva tarea")
        }
    }
}

@Composable
fun PantallaTextosInferiores (uiState: TareasUiState){
    Column (modifier = Modifier
        .background(Color.LightGray)
        .padding(10.dp)) {
        Text(text = "Última acción:\n ${uiState.textoUltAccion}",
            Modifier
                .fillMaxWidth()
                .background(Color.Yellow))
        Text(text = "Resumen:\n ${uiState.textoResumen}",
            Modifier
                .fillMaxWidth()
                .background(Color.White))
        Text(text = "${uiState.textoTotalHoras}",
            Modifier
                .fillMaxWidth()
                .background(Color.Yellow))
    }
}
