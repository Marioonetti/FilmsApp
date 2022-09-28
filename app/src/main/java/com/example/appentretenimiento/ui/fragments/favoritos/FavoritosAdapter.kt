package com.example.appentretenimiento.ui.fragments.favoritos

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.appentretenimiento.R
import com.example.appentretenimiento.databinding.RvFavoritosVistaElementosBinding
import com.example.appentretenimiento.domain.model.SeriePelicula

class FavoritosAdapter(
    val context: Context,
    val accionesFavs: AccionesFavs,
) : ListAdapter<SeriePelicula, FavoritosAdapter.FavoritosViewHolder>(DiffCallback()) {


    interface AccionesFavs {

        fun muestraInfo(seriePelicula: SeriePelicula)
        fun isItemSelected(seriePelicula: SeriePelicula): Boolean
        fun selectItem(seriePelicula: SeriePelicula)
        fun deselectItem(seriePelicula: SeriePelicula)
        fun noHaySeleccionados(): Boolean
        fun startSelectMode()
        fun endSelectMode()
        fun eliminarFav(seriePelicula: SeriePelicula)
        fun changeBarNumber()

    }

    private var selectedMode: Boolean = false

    fun resetSelectMode() {
        accionesFavs.endSelectMode()
        selectedMode = false
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {

        return FavoritosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_favoritos_vista_elementos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)

    }


    inner class FavoritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvFavoritosVistaElementosBinding.bind(itemView)

        fun bind(seriePelicula: SeriePelicula) {

            itemView.setOnLongClickListener {
                if (!selectedMode) {
                    selectedMode = true
                    accionesFavs.selectItem(seriePelicula)
                    itemView.setBackgroundColor(Color.BLUE)
//                    Cambia a la barra contextual
                    accionesFavs.startSelectMode()
                    accionesFavs.changeBarNumber()
                    notifyDataSetChanged()
                }
                true
            }


            itemView.setOnClickListener {
                if (selectedMode) {

                    if (accionesFavs.isItemSelected(seriePelicula)) {
                        accionesFavs.deselectItem(seriePelicula)
                        itemView.setBackgroundColor(Color.WHITE)
//                        Comprobar que no hay mas seleccionados
                        if (accionesFavs.noHaySeleccionados()) {
                            resetSelectMode()
                        }
                    } else {
                        accionesFavs.selectItem(seriePelicula)
                        itemView.setBackgroundColor(Color.BLUE)
                    }
                    accionesFavs.changeBarNumber()
                } else {
                    accionesFavs.muestraInfo(seriePelicula)
                }

            }

            with(binding) {
                var request = ImageRequest.Builder(context)
                    .data(seriePelicula.poster)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .target(
                        onStart = {
                            pbImageFavs.visibility = View.VISIBLE
                        },
                        onSuccess = {
                            pbImageFavs.visibility = View.GONE
                            imgFav.setImageDrawable(it)
                        },
                        onError = {
                            pbImageFavs.visibility = View.GONE
                        }
                    ).build()

                context.imageLoader.enqueue(request)
                tvNombreFav.text = seriePelicula.nombre
                tvTipo.text = seriePelicula.tipo
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


    val swipeGesture = object : SwipeGesture(context) {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (!selectedMode) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        accionesFavs.eliminarFav(currentList[viewHolder.adapterPosition])
                    }
                }
            }
        }
    }


}