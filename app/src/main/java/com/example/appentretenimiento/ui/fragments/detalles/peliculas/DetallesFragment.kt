package com.example.appentretenimiento.ui.fragments.detalles.peliculas

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
import com.example.appentretenimiento.databinding.DetallesFragmentBinding
import com.example.appentretenimiento.domain.model.Pelicula
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetallesFragment : Fragment() {

    private var _binding: DetallesFragmentBinding? = null
    private val binding get() = _binding!!


    private val args: DetallesFragmentArgs by navArgs()

    private val viewModel: DetallesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetallesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        cargaFunciones(id)
        configBoton(id)


    }


    private fun loadData(pelicula: Pelicula) {


        binding.ivStar.load(Variables.IMAGE_ESTRELLA)

        with(binding) {
            imgPoster.load(pelicula.poster)
            tvNombre.text = pelicula.nombre
            tvOverview.text = pelicula.descrip
            tvScore.text = pelicula.rating.toString()
        }

    }

    private fun cargaFunciones(id: Int) {


        viewModel.controlaEventos(DetallesPeliculasContract.DetallesPeliculasEvent.PedirPelicula(id))



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.pbDetallesPeliculas.visibility =
                        if (value.loading) View.VISIBLE else View.GONE

                    value.pelicula?.let {
                        loadData(it)
                    }

                    if (value.guardado) {
                        binding.imgFav.load(Variables.IMAGEN_CORAZON_ROJO)
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


    }

    private fun configBoton(idPelicula: Int) {
        binding.imgFav.setOnClickListener {
            if (viewModel.uiState.value.guardado) {
                viewModel.controlaEventos(
                    DetallesPeliculasContract.DetallesPeliculasEvent.BorrarPelicula(
                        idPelicula
                    )
                )
                binding.imgFav.load(Variables.IMAGE_CORAZON_VACIO)
            } else {
                viewModel.uiState.value.pelicula?.let {
                    viewModel.controlaEventos(
                        DetallesPeliculasContract.DetallesPeliculasEvent.InsertPelicula(it)
                    )
                    binding.imgFav.load(Variables.IMAGEN_CORAZON_ROJO)
                }

            }
        }
    }


}