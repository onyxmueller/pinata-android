package net.onyxmueller.pinataandroiddemo.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.onyxmueller.pinataandroiddemo.R
import net.onyxmueller.pinataandroiddemo.data.DemoFile

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, fileAdapter: FileAdapter) {
        view.adapter = fileAdapter
    }

    @JvmStatic
    @BindingAdapter("adapterFileList")
    fun bindAdapterFileList(view: RecyclerView, files: List<DemoFile>?) {
        if (!files.isNullOrEmpty()) {
            (view.adapter as? FileAdapter)?.addFileList(files)
        }
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: AppCompatImageView, url: String) {
        Picasso.get().load(url).error(R.drawable.non_image).into(view)
    }
}
