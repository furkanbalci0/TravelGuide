package com.furkanbalci.travelguide.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.databinding.DetailImageItemBinding
import com.furkanbalci.travelguide.databinding.FragmentDetailBinding
import com.furkanbalci.travelguide.util.extensions.download

class DetailImageAdapter(
    private val detailBinding: FragmentDetailBinding,
    private val imageList: List<String>
) :
    RecyclerView.Adapter<DetailImageAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: DetailImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String, position: Int) {
            binding.ivDetailImage.let { miniImage ->
                miniImage.download(imageUrl)
                miniImage.setOnClickListener {
                    if (miniImage.drawable != detailBinding.mainImage.drawable) {
                        detailBinding.mainImage.download(imageUrl)
                        val preferences = detailBinding.root.context.getSharedPreferences("com.furkanbalci.travelguide", 0)
                        preferences.edit().putInt("selected-detail-image-position", position).apply()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DetailImageItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position], position)
    }

    override fun getItemCount(): Int {
        return this.imageList.size
    }
}