package art.example.artkoin1

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class StateVM(application: Application) : AndroidViewModel(application), KoinComponent {
    private val prefs = application.getSharedPreferences("state_vm_prefs", Context.MODE_PRIVATE)
    private var _zmienna1 = MutableStateFlow("zmianna3_A")
    val zmienna1: StateFlow<String> = _zmienna1.asStateFlow()
    val repo by inject<Repo>()

    fun setZmienna1(s: String, save: Boolean = false) {
        _zmienna1.value = s
        Toast.makeText(application.applicationContext, "VM Set zmienna",
            Toast.LENGTH_SHORT).show()
        if (save) {
            prefs.edit { putString("zmienna1", s) }
        }
    }

    fun restoreZmienna1() {
        val saved = prefs.getString("zmienna1", null)
        if (saved != null) {
            _zmienna1.value = saved
        }
    }

    fun getRepo() : String = repo.repoData
}