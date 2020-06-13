package com.alpha2048.mergeadaptertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alpha2048.mergeadaptertest.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.progress.visibility = View.VISIBLE

        val bannerAdapter = BannerAdapter()
        val gridItemAdapter = GridItemAdapter(viewModel)

        val mergeAdapter = MergeAdapter(
            bannerAdapter,
            gridItemAdapter
        )

        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context,3)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0) 3 else 1
            }
            layoutManager = gridLayoutManager
            adapter = mergeAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalCount = recyclerView.adapter?.itemCount
                    val childCount = recyclerView.childCount
                    val layoutManager = recyclerView.layoutManager

                    if (layoutManager is GridLayoutManager) {
                        val firstPosition = layoutManager.findFirstVisibleItemPosition()
                        if (totalCount == childCount + firstPosition + 9) {
                            binding.progress.visibility = View.VISIBLE
                            viewModel.loadData()
                        }
                    }
                }
            })
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.trigger
                .asFlow()
                .flowOn(Dispatchers.Default)
                .collect {
                    binding.progress.visibility = View.INVISIBLE
                    gridItemAdapter.notifyDataSetChanged()
                }
        }

        viewModel.loadData()
    }
}
