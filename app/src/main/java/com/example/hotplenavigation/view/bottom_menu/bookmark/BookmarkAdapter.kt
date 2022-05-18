package com.example.hotplenavigation.view.bottom_menu.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.hotplenavigation.R
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.databinding.RecyclerBookmarkItemBinding

class BookmarkAdapter(
    private var bookmarkList: List<BookmarkFragmentEntity>,
    val onClickItem: (bookmarkData: BookmarkFragmentEntity) -> Unit,
    val onClickButton: (bookmarkData: BookmarkFragmentEntity) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RecyclerBookmarkItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookmarkList[position], onClickItem, onClickButton)
    }

    override fun getItemCount(): Int = bookmarkList.size

    fun setData(newData: List<BookmarkFragmentEntity>) {
        bookmarkList = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerBookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            bookmarkData: BookmarkFragmentEntity,
            onClickItem: (bookmarkData: BookmarkFragmentEntity) -> Unit,
            onClickButton: (bookmarkData: BookmarkFragmentEntity) -> Unit
        ) {
            binding.tvPlace.text = bookmarkData.title
            binding.tvAddress.text = bookmarkData.address
            binding.ivThumb.load("https://picsum.photos/200/300") {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.DISABLED)
            }

//            binding.btnDirection.setOnClickListener {
//                onClickButton.invoke(bookmarkData)
//            }
            binding.root.setOnClickListener {
                onClickItem.invoke(bookmarkData)
            }
        }
    }
}