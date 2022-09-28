package com.example.appentretenimiento.ui.fragments.favoritos.detalles.peliculas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.appentretenimiento.databinding.FragmentFavLocalFilmsBinding
import com.example.appentretenimiento.domain.model.Pelicula
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavLocalFilms : Fragment() {


    private var _binding: FragmentFavLocalFilmsBinding? = null
    private val binding get() = _binding!!

    private val args: FavLocalFilmsArgs by navArgs()

    private val viewModel: FavLocalFilmViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavLocalFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargaFunciones()
        configBotones()

    }


    private fun loadData(pelicula: Pelicula?) {

        with(binding) {
            imgPoster.load(pelicula?.poster)
            ivStar.load(Variables.IMAGE_ESTRELLA)
            tvNombre.text = pelicula?.nombre
            tvOverview.text = pelicula?.descrip
            tvScore.text = pelicula?.rating.toString()
            pelicula?.puntuacionPersonal?.let {
                tvTituloValoracionPropia.visibility = View.VISIBLE
                tvPuntuacionPersonal.visibility = View.VISIBLE
                btnPuntuacionPersonal.visibility = View.GONE
                tfPuntuacion.visibility = View.GONE
                tvPuntuacionPersonal.text = pelicula.puntuacionPersonal.toString()
            }

            pelicula?.visto?.let {
                if (it) {
                    tvVisto.visibility = View.VISIBLE
                    btnVisto.visibility = View.GONE
                }

            }
        }


    }

    private fun cargaFunciones() {
        val id = args.id


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    value.pelicula?.let {
                        loadData(it)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uierror.collect {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.controlaEventos(FavoriteFilmContract.EventFavoriteFilm.PedirPelicula(id))


    }


    private fun configBotones() {
        binding.btnPuntuacionPersonal.setOnClickListener {
            viewModel.uiState.value.pelicula?.let { pelicula ->
                if (!binding.tfPuntuacionTexto.text.isNullOrBlank()) {
                    pelicula.puntuacionPersonal =
                        binding.tfPuntuacionTexto.text.toString().toDouble()
                    viewModel.controlaEventos(
                        FavoriteFilmContract.EventFavoriteFilm.ActualizarPelicula(
                            pelicula
                        )
                    )
                    with(binding) {
                        tvTituloValoracionPropia.visibility = View.VISIBLE
                        tvPuntuacionPersonal.visibility = View.VISIBLE
                        tvPuntuacionPersonal.text = pelicula.puntuacionPersonal.toString()
                        btnPuntuacionPersonal.visibility = View.GONE
                        tfPuntuacion.visibility = View.GONE
                    }
                }
            }

        }
        binding.btnVisto.setOnClickListener {
            viewModel.uiState.value.pelicula?.let { pelicula ->
                pelicula.visto = true
                viewModel.controlaEventos(
                    FavoriteFilmContract.EventFavoriteFilm.ActualizarPelicula(
                        pelicula
                    )
                )
                binding.btnVisto.visibility = View.GONE
                binding.tvVisto.visibility = View.VISIBLE
            }
        }

    }

}