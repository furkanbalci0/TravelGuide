package com.furkanbalci.travelguide.presentation.guide

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.article.Article
import com.furkanbalci.travelguide.databinding.ArticleItemBinding
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.util.PreferencesUtil
import com.furkanbalci.travelguide.util.PreferencesUtil.set

class ArticleAdapter(private val results: List<DetailObject>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        //Get liked articles id list from shared preferences
        private val likedList: MutableSet<String> = PreferencesUtil.defaultPrefs(binding.root.context)
            .getString("liked", "")!!.split(",").toMutableSet()

        fun bind(result: Article) {

            //Set detail object to result.
            binding.detailObject = result

            //If article is liked, set like icon color to selected color.
            if (likedList.contains(result.id)) {
                binding.likeImage.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.selected_like_icon))
            }

            //Like button.
            binding.likeButton.setOnClickListener {

                //If article contains in liked list, remove it else add it and change icon color.
                if (likedList.contains(result.getCustomId())) {
                    likedList.remove(result.getCustomId())
                    binding.likeImage.setColorFilter(ContextCompat.getColor(it.context, R.color.unselected_like_icon))
                    Toast.makeText(binding.root.context, R.string.text_remove_like, Toast.LENGTH_SHORT).show()
                } else {
                    likedList.add(result.getCustomId())
                    binding.likeImage.setColorFilter(ContextCompat.getColor(it.context, R.color.selected_like_icon))
                    Toast.makeText(binding.root.context, R.string.text_add_like, Toast.LENGTH_SHORT).show()
                }

                //Save liked articles id list to shared preferences.
                PreferencesUtil.defaultPrefs(binding.root.context)["liked"] = likedList.joinToString(",")
            }

            //Set on click listener to navigate to detail fragment.
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
        holder.bind(this.results[position] as Article)
    }

    override fun getItemCount(): Int {
        return this.results.size
    }
}