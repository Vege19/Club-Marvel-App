package github.vege19.clubmarvel.utils

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//Launch new activity
inline fun <reified T : FragmentActivity> FragmentActivity.launchActivity(
        closeCurrent: Boolean = false,
        noinline init: Intent.() -> Unit = {}) {
    val i = Intent(this, T::class.java)
    i.init()
    startActivity(i)
    if (closeCurrent) {
        finish()
    }
}

//Make retrofit callback
fun ViewModel.makeRetrofitCallback(action: suspend () -> Unit) {
    viewModelScope.launch {
        withContext(Dispatchers.IO) {
            action.invoke()
        }
    }
}