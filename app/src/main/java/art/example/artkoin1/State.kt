package art.example.artkoin1

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.application
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun State(navController: NavController, vm: StateVM = koinViewModel()) {
    val zmienna by vm.zmienna1.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var shouldSave by remember { mutableStateOf(false) }
    var repoTxt by remember { mutableStateOf("") }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, state ->
            when (state) {
                Lifecycle.Event.ON_RESUME -> vm.restoreZmienna1()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Zmienna: $zmienna")
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Column() {
                Checkbox(
                    checked = shouldSave,
                    onCheckedChange = { shouldSave = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Czy zapisywać")
            }
            Button(onClick = { vm.setZmienna1("zminna1_B", save = shouldSave)}) {
                Text("Zmień wartość")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
                    repoTxt = vm.repo.repoData
               })
        {
            Text("Repo text")
        }
        if (repoTxt.isNotBlank()) {
            Text(text = "repo text: $repoTxt")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {navController.navigate(Pages.Start.name)}) {
            Text("Strona Start")
        }
    }
}