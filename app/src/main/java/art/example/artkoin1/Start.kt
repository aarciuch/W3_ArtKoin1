package art.example.artkoin1

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Start(navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var zmienna1 = "zmienna1_A"
    var zmienna2 by remember { mutableStateOf("zmienna2_A") }
    var zmienna3 by rememberSaveable { mutableStateOf("zmienna3_A") }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, state ->
            when (state) {
                Lifecycle.Event.ON_CREATE -> getLogInfo("ON_CREATE", context)
                Lifecycle.Event.ON_START -> getLogInfo("ON_START", context)
                Lifecycle.Event.ON_RESUME -> getLogInfo("ON_RESUME", context)
                Lifecycle.Event.ON_PAUSE -> getLogInfo("ON_PAUSE", context)
                Lifecycle.Event.ON_STOP -> getLogInfo("ON_STOP", context)
                Lifecycle.Event.ON_DESTROY -> getLogInfo("ON_DESTROY", context)
                Lifecycle.Event.ON_ANY -> getLogInfo("ON_ANY", context)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = { Text("Moja aplikacja") })
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.navigate(Pages.Start.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "remember",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Pages.State.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowCircleUp,
                            contentDescription = "VM",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 0.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
                    .background(Color.Cyan)
            ) {

                Column {
                    Text(
                        text = "Zmienne:\nzmienna1 = ${zmienna1}\nzmienna2 = ${zmienna2}\nzmienna3 = ${zmienna3}",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Yellow)
                            .fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            zmienna1 = "zmienna1_B"
                            zmienna2 = "zmienna2_B"
                            zmienna3 = "zmienna3_B"},
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 0.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
                    ) {
                        Text("Modyfikacja zmiennych")
                    }
                }
            }
        }
    )
}

fun getLogInfo(state: String, context: Context) {
    Log.i("LIFECYCLE", state)
    Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
}