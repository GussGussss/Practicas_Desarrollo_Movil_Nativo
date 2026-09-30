package com.example.practica_6_lazycolumn_y_listas_dinamicas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListaScreen(){
    //Obtener la lista de contactos
    val contactos = obtenerContactosDummy()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
                                //espaciado entre elementos de la lista
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(contactos) { contacto ->
            ContactoItem(contacto = contacto)
        }
    }
}
@Composable
fun ContactoItem(contacto: Contacto){
    //Card muestra los datos de la lista
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) { }
}

@Preview(showBackground = true)
@Composable
fun PreviewListaScreen() {
    ListaScreen();
}