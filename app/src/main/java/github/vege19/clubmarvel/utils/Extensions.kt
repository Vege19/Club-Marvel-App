package github.vege19.clubmarvel.utils

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import github.vege19.clubmarvel.R
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
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

//Set image with glide
fun ImageView.setGlideImage(url: String, context: Context, hasZoomAnimation: Boolean, width: Int?, height: Int?) {
    if (hasZoomAnimation) {
        Glide.with(context)
                .load(url)
                .transition(GenericTransitionOptions.with(R.anim.anim_zoom_out))
                .centerCrop()
                .override(width?:0, height?:0)
                .into(this)
    } else {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .override(width!!, height!!)
                .into(this)
    }
}

//Navigation
fun FragmentActivity.navigateTo(view: View, actionId: Int, bundle: Bundle?) {
    Navigation.findNavController(view).navigate(actionId, bundle)
}

//Action bar config

fun FragmentActivity.configureActionbar(context: Context, actionBar: View, title: String, isReturnable: Boolean, action: (actionBar: View) -> Unit) {
    actionBar._fragment_tb.title = title
    actionBar._fragment_tb.setTitleTextAppearance(
            context,
            R.style.ActionBarTitleAppearance
    )

    if (isReturnable) {
        actionBar.setPadding(0, 0, 300, 0)
        actionBar._fragment_tb.navigationIcon = context.getDrawable(R.drawable.ic_arrow_back_24dp)
        action.invoke(actionBar)
    }
}
