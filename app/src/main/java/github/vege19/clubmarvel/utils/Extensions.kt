package github.vege19.clubmarvel.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.ethanhua.skeleton.Skeleton
import github.vege19.clubmarvel.App
import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.models.ComicModel
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

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
    val progressDrawable = CircularProgressDrawable(App.getContext())
    progressDrawable.setColorSchemeColors(App.getContext().resources.getColor(R.color.colorAccent))
    progressDrawable.centerRadius = 30f
    progressDrawable.strokeWidth = 5f
    progressDrawable.start()

    if (hasZoomAnimation) {
        Glide.with(context)
                .load(url)
                .placeholder(progressDrawable)
                .transition(GenericTransitionOptions.with(R.anim.anim_zoom_out))
                .error(App.getContext().getDrawable(R.drawable.ic_close_24dp))
                .centerCrop()
                .override(width?:0, height?:0)
                .into(this)
    } else {
        Glide.with(context)
                .load(url)
                .placeholder(progressDrawable)
                .error(App.getContext().getDrawable(R.drawable.ic_vector_not_found))
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
            github.vege19.clubmarvel.R.style.ActionBarTitleAppearance
    )

    if (isReturnable) {
        actionBar.setPadding(0, 0, 300, 0)
        actionBar._fragment_tb.navigationIcon = context.getDrawable(github.vege19.clubmarvel.R.drawable.ic_arrow_back_24dp)
        action.invoke(actionBar)
    }
}

//Verify internet connection
fun isDeviceOnline(): Boolean {
    val cm = App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = cm.activeNetworkInfo
    if (ni != null && ni.isConnected) {
        return true
    }
    return false
}

//Show alert dialog
fun FragmentActivity.showAlertDialog(context: Context, title: String, message: String) {
    AlertDialog
        .Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
        .create()
        .show()
}

fun showSkeleton(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<GenericAdapter.ViewHolder>, layout: Int) {
    Skeleton.bind(recyclerView).adapter(adapter).load(layout).show()
}