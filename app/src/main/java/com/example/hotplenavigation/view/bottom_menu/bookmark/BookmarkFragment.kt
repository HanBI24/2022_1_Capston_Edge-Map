package com.example.hotplenavigation.view.bottom_menu.bookmark

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingFragment
import com.example.hotplenavigation.databinding.FragmentBookmarkBinding
import com.example.hotplenavigation.view.bottom_menu.bookmark.webview.WebViewActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

// 즐겨찾기 Fragment (Local DB: Android Jetpack Room Library)
@AndroidEntryPoint
class BookmarkFragment : BindingFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {
    private val bookmarkViewModel: BookmarkFragmentViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun initView() {
        initRecyclerView()

        // 모든 데이터를 가져오고, Recycler View List에 추가
        bookmarkViewModel.getAllData().observe(this, {
            bookmarkAdapter.setData(it)
        })
    }

    private fun initRecyclerView() {
        // 레이아웃 매니저 설정
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }
        // 기준선 그리기
        val decoration = DividerItemDecoration(activity, linearLayoutManager.orientation)

        // 해당 RecyclerView Adapter 설정
        bookmarkAdapter = BookmarkAdapter(
            emptyList(),
            onClickItem = {
//                searchResultViewModel.touchItem(it)
//                sheet.show(activity?.supportFragmentManager!!, "BookmarkFragment")
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("get_address", it.address)
                startActivity(intent)
            },
            // 버튼 클릭 시 해당 장소의 정보를 표시하는 웹뷰 Activity 이동
            onClickButton = {
            }
        )

        // RecyclerView 설정
        binding.rvBookmark.apply {
            layoutManager = linearLayoutManager
            adapter = bookmarkAdapter
            addItemDecoration(decoration)
        }

        // 오른쪽으로 스와이프 시 즐겨찾기 목록 및 Local DB에서 삭제
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                bookmarkViewModel.deleteBookmark(bookmarkViewModel.getAllData().value!![viewHolder.adapterPosition].title)
                Snackbar.make(binding.container, "삭제되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show()
            }
        }).apply {
            attachToRecyclerView(binding.rvBookmark)
        }
    }
}
