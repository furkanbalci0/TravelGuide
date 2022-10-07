package com.furkanbalci.travelguide.presentation.guide.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.ArticleItemBinding
import com.furkanbalci.travelguide.di.DetailObject

class ArticleAdapter(private val results: List<DetailObject>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: DetailObject) {
            binding.detailObject = result
            binding.root.setOnClickListener {
                val bundle = bundleOf("detailObject" to result)
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_guide_to_detailFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.results[position])
    }

    override fun getItemCount(): Int {
        return this.results.size
    }
}