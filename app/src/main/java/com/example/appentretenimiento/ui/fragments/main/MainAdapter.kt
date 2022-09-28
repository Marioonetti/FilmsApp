package com.example.appentretenimiento.ui.fragments.main

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
import com.example.appentretenimiento.databinding.RvMainVistaElementosBinding
import com.example.appentretenimiento.domain.model.SeriePelicula

class MainAdapter(
    val context: Context,
    val acciones: Interacciones

) : ListAdapter<SeriePelicula, MainAdapter.MainViewHolder>(DiffCallback()) {


    interface Interacciones {

        fun muestraInfo(seriePelicula: SeriePelicula)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_main_vista_elementos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)

    }


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvMainVistaElementosBinding.bind(itemView)

        fun bind(seriePelicula: SeriePelicula) {


            itemView.setOnClickListener {
                acciones.muestraInfo(seriePelicula)
            }



            with(binding) {
                var request = ImageRequest.Builder(context)
                    .data(seriePelicula.poster)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .target(
                        onStart = {
                            pbImage.visibility = View.VISIBLE
                        },
                        onSuccess = {
                            pbImage.visibility = View.GONE
                            imgPortada.setImageDrawable(it)
                        },
                        onError = {
                            pbImage.visibility = View.GONE
                        }
                    ).build()

                context.imageLoader.enqueue(request)
                tvNombre.text = seriePelicula.nombre
            }


        }


    }


    class DiffCallback : DiffUtil.ItemCallback<SeriePelicula>() {
        override fun areItemsTheSame(oldItem: SeriePelicula, newItem: SeriePelicula): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeriePelicula, newItem: SeriePelicula): Boolean {
            return oldItem == newItem
        }
    }


}