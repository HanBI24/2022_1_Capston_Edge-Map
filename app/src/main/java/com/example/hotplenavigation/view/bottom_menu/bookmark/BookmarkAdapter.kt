package com.example.hotplenavigation.view.bottom_menu.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.hotplenavigation.R
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.databinding.RecyclerBookmarkItemBinding
import com.example.hotplenavigation.util.extension.removeHtmlTag

// 즐겨찾기 화면에 있는 RecyclerView 사용을 위한 Adapter 설정
class BookmarkAdapter(
    private var bookmarkList: List<BookmarkFragmentEntity>,
    val onClickItem: (bookmarkData: BookmarkFragmentEntity) -> Unit,
    val onClickButton: (bookmarkData: BookmarkFragmentEntity) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.MyViewHolder>() {

    // ViewHolder 설정
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RecyclerBookmarkItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    // ViewHolder Binding
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookmarkList[position], onClickItem, onClickButton)
    }

    // 리스트 갯수
    override fun getItemCount(): Int = bookmarkList.size

    // RecyclerView에 데이터 추가
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
            // 이름 및 주소, 사진 설정
            binding.tvPlace.text = removeHtmlTag(bookmarkData.title)
            binding.tvAddress.text = removeHtmlTag(bookmarkData.address)
//            binding.ivThumb.load("https://picsum.photos/200/300") {
//                crossfade(true)
//                placeholder(R.drawable.ic_launcher_foreground)
//                transformations(CircleCropTransformation())
//                memoryCachePolicy(CachePolicy.DISABLED)
//            }

            // 버튼 클릭 이벤트
//            binding.btnWeb.setOnClickListener {
//                onClickButton.invoke(bookmarkData)
//            }
            binding.root.setOnClickListener {
                onClickItem.invoke(bookmarkData)
            }
        }
    }
}
