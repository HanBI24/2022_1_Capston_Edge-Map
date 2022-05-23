package com.example.hotplenavigation.view.bottom_menu.search

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingFragment
import com.example.hotplenavigation.databinding.FragmentSearchBinding
import com.example.hotplenavigation.view.bottom_menu.search.bottom_sheet.SearchBottomSheetFragment
import com.example.hotplenavigation.view.bottom_menu.search.search_result.SearchResultActivity
import com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragment
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IndexOutOfBoundsException
import java.util.*

// 검색 Fragment
@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchFragmentViewModel: SearchFragmentViewModel by activityViewModels()
    private val sheet: SearchBottomSheetFragment by lazy { SearchBottomSheetFragment() }

    override fun initView() {
        multiSearchViewInit()
        observeData()
        onClickListener()
    }

    // 검색 기능: 오픈소스 라이브러리 활용
    private fun multiSearchViewInit() {
        binding.multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener {
            override fun onItemSelected(index: Int, s: CharSequence) {
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("search_fragment", s.toString())
                startActivity(intent)
            }

            override fun onSearchItemRemoved(index: Int) {
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
            }
        })
    }

    private fun observeData() {
        for(i in 0..5) {
            searchFragmentViewModel.getDuruData(
                "qcjQXVRmqbxl/++PgL+DmjuHYAoLUDxFVyZcI70vaGOrYXYAWdBmNIEXwuNH7impD0bkwGKhWiX4IRTQB1aPXQ==",
                Random().nextInt(536) + 1,
                1,
                i
            )
        }

        searchFragmentViewModel.apply {
            getDuruData1.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle1.text = it[0].response.body.items.item.themeNm
                        tvLineMsg1.text = it[0].response.body.items.item.linemsg
                        tvDesc1.text = removeHtmlTag(it[0].response.body.items.item.linemsg)
                    } catch(idx: IndexOutOfBoundsException) {
                        tvTitle1.text = "준비중..."
                        tvLineMsg1.text = "준비중..."
                        tvDesc1.text = "준비중..."
                    }
                }
            })

            getDuruData2.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle2.text = it[1].response.body.items.item.themeNm
                        tvLineMsg2.text = it[1].response.body.items.item.linemsg
                        tvDesc2.text = removeHtmlTag(it[1].response.body.items.item.linemsg)
                    } catch(idx: IndexOutOfBoundsException) {
                        tvTitle2.text = "준비중..."
                        tvLineMsg2.text = "준비중..."
                        tvDesc2.text = "준비중..."
                    }
                }
            })

            getDuruData3.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle3.text = it[2].response.body.items.item.themeNm
                        tvLineMsg3.text = it[2].response.body.items.item.linemsg
                        tvDesc3.text = removeHtmlTag(it[2].response.body.items.item.linemsg)
                    } catch (idx: IndexOutOfBoundsException) {
                        tvTitle3.text = "준비중..."
                        tvLineMsg3.text = "준비중..."
                        tvDesc3.text = "준비중..."
                    }
                }
            })

            getDuruData4.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle4.text = it[3].response.body.items.item.themeNm
                        tvLineMsg4.text = it[3].response.body.items.item.linemsg
                        tvDesc4.text = removeHtmlTag(it[3].response.body.items.item.linemsg)
                    } catch (idx: IndexOutOfBoundsException) {
                        tvTitle4.text = "준비중..."
                        tvLineMsg4.text = "준비중..."
                        tvDesc4.text = "준비중..."
                    }
                }
            })
        }
    }

    private fun onClickListener() {
        binding.apply {
            container1.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 1
                fragmentManager?.let { it1 -> sheet.show(it1, "SearchBottomSheetFragment") }
            }
            container2.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 2
                fragmentManager?.let { it1 -> sheet.show(it1, "SearchBottomSheetFragment") }
            }
            container3.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 3
                fragmentManager?.let { it1 -> sheet.show(it1, "SearchBottomSheetFragment") }
            }
            container4.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 4
                fragmentManager?.let { it1 -> sheet.show(it1, "SearchBottomSheetFragment") }
            }
        }
    }

    private fun removeHtmlTag(desc: String) =  desc.replace("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
}
