package com.example.appentretenimiento.ui.fragments.busqueda

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
import com.example.appentretenimiento.databinding.RvSearchElementsBinding
import com.example.appentretenimiento.domain.model.SeriePeliculaActor

class SearchAdapter(
    val context: Context,
    val acciones: Acciones

) : ListAdapter<SeriePeliculaActor, SearchAdapter.SearchViewHolder>(SearchAdapter.DiffCallback()) {

    interface Acciones {
        fun muestraInfo(seriePeliculaActor: SeriePeliculaActor)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {

        return SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_search_elements, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) =
        with(holder) {
            val item = getItem(position)
            bind(item)

        }


    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvSearchElementsBinding.bind(itemView)

        fun bind(seriePeliculaActor: SeriePeliculaActor) {

            with(binding) {
                var request = ImageRequest.Builder(context)
                    .data(seriePeliculaActor.poster)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .target(
                        onStart = {
                            pbImage.visibility = View.VISIBLE
                        },
                        onSuccess = {
                            pbImage.visibility = View.GONE
                            imgSearch.setImageDrawable(it)
                        },
                        onError = {
                            pbImage.visibility = View.GONE
                        }
                    ).build()

                context.imageLoader.enqueue(request)
                tvNombreSearch.text = seriePeliculaActor.nombre
                tvRating.text = seriePeliculaActor.valoracion.toString()
            }

            itemView.setOnClickListener {
                acciones.muestraInfo(seriePeliculaActor)
            }


        }

    }


    class DiffCallback : DiffUtil.ItemCallback<SeriePeliculaActor>() {
        override fun areItemsTheSame(
            oldItem: SeriePeliculaActor,
            newItem: SeriePeliculaActor
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SeriePeliculaActor,
            newItem: SeriePeliculaActor
        ): Boolean {
            return oldItem == newItem
        }
    }

}