package com.example.practica22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// 1. DEFINICIÓN DE PANTALLAS COMO CLAVES (KEYS)
// En lugar de usar strings o rutas URL, definimos objetos y clases
// que representan cada destino de manera fuertemente tipada.
// ==========================================
sealed interface ArtNavKey {
    data object Home : ArtNavKey
    data class Detail(
        val title: String,
        val author: String,
        val description: String,
        val imageRes: Int
    ) : ArtNavKey
}

// Modelo de datos para las obras de arte
data class Artwork(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val imageRes: Int
)

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtGalleryApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtGalleryApp() {
    // ==========================================
    // 2. CREACIÓN Y PERSISTENCIA DEL BACKSTACK
    // Mantenemos una lista mutable de claves que representa
    // la pila de navegación explícita controlada por el estado.
    // ==========================================
    val backStack = remember { mutableStateListOf<ArtNavKey>(ArtNavKey.Home) }

    // Manejo del botón atrás del sistema para desapilar pantallas
    BackHandler(enabled = backStack.size > 1) {
        backStack.removeAt(backStack.size - 1)
    }

    val beigeBackground = Color(0xFFF5F5DC)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = beigeBackground
    ) {
        Scaffold(
            containerColor = beigeBackground,
            topBar = {
                TopAppBar(
                    title = { Text("Galería de Arte Digital (Nav 3)", color = Color.Black) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFEFEBD9)),
                    navigationIcon = {
                        // Botón de retroceso en la barra superior si hay más de una pantalla en la pila
                        if (backStack.size > 1) {
                            IconButton(onClick = { backStack.removeAt(backStack.size - 1) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Regresar",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // ==========================================
                // 3 & 10. NAVDISPLAY Y NAVEGACIÓN ADAPTATIVA (SCENES / TWO-PANE)
                // Evaluamos el ancho de pantalla para decidir si mostramos
                // un diseño adaptable de dos paneles (Tablet) o de un solo panel (Móvil).
                // ==========================================
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val isTablet = maxWidth > 600.dp

                    if (isTablet) {
                        // Modo Tablet / Pantalla Grande: Muestra Lista y Detalle simultáneamente (Split-View)
                        var selectedArt by remember { mutableStateOf<Artwork?>(sampleArtworks.first()) }
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.weight(1f)) {
                                ArtHomeScreen(
                                    selectedId = selectedArt?.id,
                                    onArtSelected = { art -> selectedArt = art }
                                )
                            }
                            VerticalDivider(modifier = Modifier.fillMaxHeight().width(1.dp), color = Color.LightGray)
                            Box(modifier = Modifier.weight(1f)) {
                                if (selectedArt != null) {
                                    ArtDetailScreen(
                                        detailKey = ArtNavKey.Detail(
                                            title = selectedArt!!.title,
                                            author = selectedArt!!.author,
                                            description = selectedArt!!.description,
                                            imageRes = selectedArt!!.imageRes
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        // ==========================================
                        // 4 & 9. ENTRYPROVIDER Y ANIMACIONES DE TRANSICIÓN
                        // AnimatedContent observa el último elemento del BackStack
                        // y aplica animaciones de deslizamiento y fundido entre pantallas.
                        // ==========================================
                        AnimatedContent(
                            targetState = backStack.last(),
                            transitionSpec = {
                                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                    slideOutHorizontally { width -> -width } + fadeOut()
                                )
                            },
                            label = "Nav3Animation"
                        ) { currentKey ->
                            when (currentKey) {
                                is ArtNavKey.Home -> {
                                    ArtHomeScreen(
                                        selectedId = null,
                                        onArtSelected = { art ->
                                            // Navegación Home -> Detalle: Añade la clave Detail al BackStack
                                            backStack.add(
                                                ArtNavKey.Detail(
                                                    title = art.title,
                                                    author = art.author,
                                                    description = art.description,
                                                    imageRes = art.imageRes
                                                )
                                            )
                                        }
                                    )
                                }
                                is ArtNavKey.Detail -> {
                                    ArtDetailScreen(detailKey = currentKey)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Lista de obras de arte de ejemplo
val sampleArtworks = listOf(
    Artwork(1, "El grito", "Edvard Munch", "Una exploración de formas puras y colores vibrantes que evocan la arquitectura moderna y el movimiento futurista.", R.drawable.c1),
    Artwork(2, "Horizonte Digital", "Alejandro R.", "Paisaje atmosférico generado mediante técnicas mixtas digitales, representando la conexión entre naturaleza y código.", R.drawable.c2),
    Artwork(3, "comedia y la tragedia", "Valeria Soto", "Retrato expresionista surrealista que examina la identidad humana en la era de la inteligencia artificial.", R.drawable.c3),
    Artwork(4, "El hijo del hombre", " René Magritte", "Juego óptico de líneas y contrastes que transmiten el dinamismo y la velocidad de las grandes metrópolis.", R.drawable.c4),
    Artwork(5, "Armonía Silenciosa", "Lucía Montero", "Composición minimalista de tonos tierra y texturas suaves que inspiran calma y contemplación.", R.drawable.c5)
)

// ==========================================
// 8. PANTALLA PRINCIPAL (HOME SCREEN)
// Muestra la cuadrícula con las obras de arte.
// (Aquí puedes editar o agregar más obras en `sampleArtworks` arriba)
// ==========================================
@Composable
fun ArtHomeScreen(selectedId: Int?, onArtSelected: (Artwork) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Obras Destacadas",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(sampleArtworks) { art ->
                ArtCard(
                    artwork = art,
                    isSelected = art.id == selectedId,
                    onClick = { onArtSelected(art) }
                )
            }
        }
    }
}

@Composable
fun ArtCard(artwork: Artwork, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFD4CFA9) else Color(0xFFEFEBD9)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                // NOTA: Las imágenes provienen de los recursos drawable (c1, c2, c3, c4, c5).
                // Puedes cambiar o agregar tus propias imágenes en la carpeta res/drawable.
                painter = painterResource(id = artwork.imageRes),
                contentDescription = artwork.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = artwork.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1
            )
            Text(
                text = artwork.author,
                fontSize = 12.sp,
                color = Color.DarkGray,
                maxLines = 1
            )
        }
    }
}

// ==========================================
// PANTALLA DE DETALLE (DETAIL SCREEN)
// Muestra la información ampliada de la obra seleccionada.
// ==========================================
@Composable
fun ArtDetailScreen(detailKey: ArtNavKey.Detail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = detailKey.imageRes),
            contentDescription = detailKey.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = detailKey.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Autor: ${detailKey.author}",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        Text(
            text = detailKey.description,
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = 20.sp
        )
    }
}
