package sayaradz

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("dd-mm-yyyy' 'HH:mm")
            .format(systemTime).toString()
}

fun inflateImageFromUrl(url: String, imageView: ImageView, placeholder: Int, errorRes: Int, token: String) {
    val imageUrl = GlideUrl(url, LazyHeaders.Builder()
            .addHeader("Authorization", token)
            .build())
    Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions()
                    .placeholder(placeholder)
                    .error(errorRes))
            .into(imageView)
}