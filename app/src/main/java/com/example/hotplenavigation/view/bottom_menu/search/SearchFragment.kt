package com.example.hotplenavigation.view.bottom_menu.search

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingFragment
import com.example.hotplenavigation.databinding.FragmentSearchBinding
import com.example.hotplenavigation.view.bottom_menu.search.bottom_sheet.SearchBottomSheetFragment
import com.example.hotplenavigation.view.bottom_menu.search.search_result.SearchResultActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IndexOutOfBoundsException
import java.util.Random

// 검색 Fragment
@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchFragmentViewModel: SearchFragmentViewModel by activityViewModels()
    private val sheet: SearchBottomSheetFragment by lazy { SearchBottomSheetFragment() }
    private var searchWord: String = "맛집"

    override fun initView() {
        binding.rg.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.rd_food -> searchWord = "맛집"
                R.id.rd_fame -> searchWord = "관광지"
                R.id.rd_sleep -> searchWord = "숙박"
            }
        }

        initSearchView()
        observeData()
        onClickListener()
    }

    private fun initSearchView() {
        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            var handle = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("search_fragment", textView.text.toString())
                intent.putExtra("search_word_fragment", searchWord)
                searchFragmentViewModel.getRadioSelected.value = searchWord
                startActivity(intent)
                handle = true
            }
            handle
        }
    }

    private fun observeData() {
        for (i in 0..5) {
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
                    } catch (idx: IndexOutOfBoundsException) {
                        tvTitle1.text = "준비중..."
                        tvLineMsg1.text = "준비중..."
//                        tvDesc1.text = "준비중..."
                    }
                    iv1.clipToOutline = true
                }
            })

            getDuruData2.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle2.text = it[1].response.body.items.item.themeNm
                        tvLineMsg2.text = it[1].response.body.items.item.linemsg
                    } catch (idx: IndexOutOfBoundsException) {
                        tvTitle2.text = "준비중..."
                        tvLineMsg2.text = "준비중..."
                    }
                }
            })

            getDuruData3.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle3.text = it[2].response.body.items.item.themeNm
                        tvLineMsg3.text = it[2].response.body.items.item.linemsg
                    } catch (idx: IndexOutOfBoundsException) {
                        tvTitle3.text = "준비중..."
                        tvLineMsg3.text = "준비중..."
                    }
                }
            })

            getDuruData4.observe(this@SearchFragment, {
                binding.apply {
                    try {
                        tvTitle4.text = it[3].response.body.items.item.themeNm
                        tvLineMsg4.text = it[3].response.body.items.item.linemsg
                    } catch (idx: IndexOutOfBoundsException) {
                        tvTitle4.text = "준비중..."
                        tvLineMsg4.text = "준비중..."
                    }
                }
            })
        }
    }

    private fun onClickListener() {
        binding.apply {
            container1.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 1
                fragmentManager?.let { it1 ->
                    run {
                        sheet.show(it1, "SearchBottomSheetFragment")
                        searchFragmentViewModel.getPhotoNumber.value = 1
                    }
                }
            }
            container2.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 2
                fragmentManager?.let { it1 ->
                    run {
                        sheet.show(it1, "SearchBottomSheetFragment")
                        searchFragmentViewModel.getPhotoNumber.value = 2
                    }
                }
            }
            container3.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 3
                fragmentManager?.let { it1 ->
                    run {
                        sheet.show(it1, "SearchBottomSheetFragment")
                        searchFragmentViewModel.getPhotoNumber.value = 3
                    }
                }
            }
            container4.setOnClickListener {
                searchFragmentViewModel.getNumber.value = 4
                fragmentManager?.let { it1 ->
                    run {
                        sheet.show(it1, "SearchBottomSheetFragment")
                        searchFragmentViewModel.getPhotoNumber.value = 4
                    }
                }
            }
        }
    }
}
