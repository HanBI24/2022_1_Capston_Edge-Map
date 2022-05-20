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
import com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BindingFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {
    private val bookmarkViewModel: BookmarkFragmentViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private val sheet: BottomSheetFragment by lazy { BottomSheetFragment() }
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun initView() {
        initRecyclerView()

        bookmarkViewModel.getAllData().observe(this, {
            bookmarkAdapter.setData(it)
        })
    }

    private fun initRecyclerView() {
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }
        val decoration = DividerItemDecoration(activity, linearLayoutManager.orientation)

        bookmarkAdapter = BookmarkAdapter(
            emptyList(),
            onClickItem = {
//                searchResultViewModel.touchItem(it)
//                sheet.show(activity?.supportFragmentManager!!, "BookmarkFragment")
            },
            onClickButton = {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("get_address", it.address)
                startActivity(intent)
            }
        )

        binding.rvBookmark.apply {
            layoutManager = linearLayoutManager
            adapter = bookmarkAdapter
            addItemDecoration(decoration)
        }

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
