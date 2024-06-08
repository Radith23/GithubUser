package sns.example.githubusersubmission.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.rootView).load(url).apply(RequestOptions()).into(this)
}