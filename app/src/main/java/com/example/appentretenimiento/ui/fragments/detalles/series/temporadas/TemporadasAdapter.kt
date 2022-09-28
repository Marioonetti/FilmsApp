package com.example.appentretenimiento.ui.fragments.detalles.series.temporadas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.appentretenimiento.R
import com.example.appentretenimiento.databinding.RvVistaCapitulosBinding
import com.example.appentretenimiento.domain.model.Episodio

class TemporadasAdapter(val context: Context) :
    ListAdapter<Episodio, TemporadasAdapter.TemporadasViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TemporadasAdapter.TemporadasViewHolder {

        return TemporadasViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_vista_capitulos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TemporadasAdapter.TemporadasViewHolder, position: Int) =
        with(holder) {
            val item = getItem(position)
            bind(item)

        }


    inner class TemporadasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvVistaCapitulosBinding.bind(itemView)

        fun bind(episodio: Episodio) {
            with(binding) {
                var request = ImageRequest.Builder(context)
                    .data(episodio.img)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .target(
                        onStart = {
                            pbImage.visibility = View.VISIBLE
                        },
                        onSuccess = {
                            pbImage.visibility = View.GONE
                            imgCap.setImageDrawable(it)
                        },
                        onError = {
                            pbImage.visibility = View.GONE
                        }
                    ).build()

                context.imageLoader.enqueue(request)
                tvNombreCap.text = episodio.nombre
                resumenCap.text = episodio.descrip
            }


        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Episodio>() {
        override fun areItemsTheSame(oldItem: Episodio, newItem: Episodio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episodio, newItem: Episodio): Boolean {
            return oldItem == newItem
        }
    }


}