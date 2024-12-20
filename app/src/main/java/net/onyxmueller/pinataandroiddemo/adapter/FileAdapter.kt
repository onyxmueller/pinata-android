package net.onyxmueller.pinataandroiddemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import net.onyxmueller.pinataandroiddemo.data.DemoFile
import net.onyxmueller.pinataandroiddemo.databinding.ItemFileBinding

class FileAdapter : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    private val items = mutableListOf<DemoFile>()

    fun addFileList(files: List<DemoFile>) {
        items.clear()
        items.addAll(files)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context))
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            file = item
            ViewCompat.setTransitionName(itemContainer, item.name)
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = items.size

    class FileViewHolder(
        val binding: ItemFileBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}
