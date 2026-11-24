package com.example.practica22

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Chat
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.launch
import com.example.practica22.ui.theme.Practica22Theme

class MainActivity : ComponentActivity() {
//tag:MainActivity
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        ShowToast("Funcion onCreate()")
        enableEdgeToEdge()
        setContent {
            Practica22Theme {
                ViewContainer()
            }
        }
    }
    ///////////////.......///////////////////////7
    override fun onStart(){
        super.onStart()
        ShowToast("Funcion OnStart")
    }

    override fun onResume() {
        super.onResume()
        ShowToast("Funcion onResume()")
    }

    override fun onPause() {
        super.onPause()
        ShowToast("Funcion onPause()")
    }

    override fun onStop() {
        super.onStop()
        ShowToast("Funcion onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        ShowToast("Funcion onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        ShowToast("Funcion onDestroy()")
    }
    fun ShowToast(message: String) {
        val toast = Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        )
        toast.show()
        Log.i(TAG, message)
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, locale = "es-rMX")
@Composable

fun ViewContainer() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // INICIO DEL SIDEBAR
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Menú Principal",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Abrir MainActivity2") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        val intent = Intent(context, MainActivity2::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    ) {
        // FIN DEL SIDEBAR
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.home_screen)) },
                    colors = topAppBarColors(
                        containerColor = Color.Gray,
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    },
                    //////////////Falta iconos/////
                    actions = {
                        IconButton(onClick = { openWebScreen(context) }) {
                            Icon(
                                imageVector = Icons.Default.AddLink,
                                contentDescription = "Marcar como favorito",
                                tint = Color.White
                            )

                        }
                        IconButton(onClick = {
                            val intent = Intent(context, MainActivity2::class.java)
                            context.startActivity(intent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Abrir otra pantalla",
                                tint = Color.White
                            )
                        }
                        DropDownMenu()
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.bottom_app_bar),
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        },
            floatingActionButton = {

                val context = LocalContext.current

                FloatingActionButton(
                    onClick = {

                        Toast.makeText(
                            context,
                            R.string.home_fab_text,
                            Toast.LENGTH_LONG
                        ).show()

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Snackbar! <3",
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(
                           R.string.home_fab_text
                        )
                    )
                }
            }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.Cyan
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                var counter by rememberSaveable { mutableStateOf(0) }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5DC))
                ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.materia),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            textAlign = TextAlign.Center
                        )

                        Image(
                            painter = painterResource(
                                id = R.drawable.app
                            ),
                            contentDescription = stringResource(
                                id = R.string.descripcion_imagen
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.descripcion_imagen),
                            fontSize = 13.sp,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 6.dp),
                            textAlign = TextAlign.Center
                        )

                        // Carrusel de imagenes
                        Row(
                            modifier = Modifier.padding(top = 2.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Like",
                                tint = Color.Red,
                                modifier = Modifier
                                    .clickable { counter++ }
                                    .padding(start = 12.dp)
                                    .size(48.dp)
                            )

                            Text(
                                text = counter.toString(),
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.nombre),
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = stringResource(id = R.string.apellido),
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            item {
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.c1),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.c2),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.c3),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.c4),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.c5),
                                    contentDescription = "Mobile Apps"
                                )
                            }
                        }
                    }
                }
            }
        }
      }
    }
}

fun openWebScreen(context: Context, urlString: String = "https://aguascalientes.tecnm.mx/") {
    val url = Uri.parse(urlString)
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}
@Composable
fun DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.options_menu),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.whatsapp)) },
                onClick = {
                    expanded = false
                    openWebScreen(context, "https://wa.me/")
                },
                leadingIcon = {
                    Icon(Icons.Default.Chat, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.social_media)) },
                onClick = {
                    expanded = false
                    openWebScreen(context, "https://instagram.com")
                },
                leadingIcon = {
                    Icon(Icons.Default.Public, contentDescription = null)
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text(stringResource(R.string.camera)) },
                onClick = {
                    expanded = false
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    context.startActivity(intent)
                },
                leadingIcon = {
                    Icon(Icons.Default.PhotoCamera, contentDescription = null)
                }
            )
        }
    }
}
