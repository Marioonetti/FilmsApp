package com.example.appentretenimiento.ui.fragments.detalles.series

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
import com.example.appentretenimiento.databinding.RvTemporadasItemsViewBinding
import com.example.appentretenimiento.domain.model.Temporada

class DetallesSeriesAdapter(
    val context: Context,
    val acciones: AccionesSerie
) : ListAdapter<Temporada, DetallesSeriesAdapter.DettallesSeriesViewHolder>(DetallesSeriesAdapter.DiffCallback()) {

    interface AccionesSerie {

        fun verCapitulos(temporada: Temporada)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetallesSeriesAdapter.DettallesSeriesViewHolder {

        return DettallesSeriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_temporadas_items_view, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: DetallesSeriesAdapter.DettallesSeriesViewHolder,
        position: Int
    ) = with(holder) {
        val item = getItem(position)
        bind(item)

    }


    inner class DettallesSeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvTemporadasItemsViewBinding.bind(itemView)

        fun bind(temporada: Temporada) {
            with(binding) {
                var request = ImageRequest.Builder(context)
                    .data(temporada.poster)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .target(
                        onStart = {
                            pbImage.visibility = View.VISIBLE
                        },
                        onSuccess = {
                            pbImage.visibility = View.GONE
                            imgTemporada.setImageDrawable(it)
                        },
                        onError = {
                            pbImage.visibility = View.GONE
                        }
                    ).build()

                context.imageLoader.enqueue(request)
                tvNombreTemporada.text = temporada.nombre
            }

            itemView.setOnClickListener {
                acciones.verCapitulos(temporada)
            }


        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Temporada>() {
        override fun areItemsTheSame(oldItem: Temporada, newItem: Temporada): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Temporada, newItem: Temporada): Boolean {
            return oldItem == newItem
        }
    }


}