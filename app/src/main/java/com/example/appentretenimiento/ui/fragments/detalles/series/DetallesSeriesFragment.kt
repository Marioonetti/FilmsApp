package com.example.appentretenimiento.ui.fragments.detalles.series

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.appentretenimiento.databinding.FragmentDetallesSeriesBinding
import com.example.appentretenimiento.domain.model.Serie
import com.example.appentretenimiento.domain.model.Temporada
import com.example.appentretenimiento.ui.fragments.detalles.peliculas.DetallesFragmentArgs
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetallesSeriesFragment : Fragment() {

    private var _binding: FragmentDetallesSeriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetallesSeriesViewModel by viewModels()

    private val args: DetallesFragmentArgs by navArgs()

    private lateinit var adapterTemporadas: DetallesSeriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallesSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        loadRecyclerView(id)
        configBoton(id)


    }

    private fun configBoton(idSerie: Int) {
        binding.imgFav.setOnClickListener {
            if (viewModel.uiState.value.guardado) {
                viewModel.controlaEventos(SeriesContract.SeriesEvent.BorrarSerie(idSerie))
                binding.imgFav.load(Variables.IMAGE_CORAZON_VACIO)
            } else {
                viewModel.uiState.value.serie?.let {
                    viewModel.controlaEventos(
                        SeriesContract.SeriesEvent.InsertSerie(it)
                    )
                    binding.imgFav.load(Variables.IMAGEN_CORAZON_ROJO)
                }

            }
        }

    }


    private fun loadData(serie: Serie) {

        binding.ivStar.load(Variables.IMAGE_ESTRELLA)


        with(binding) {
            imgPoster.load(serie.poster)
            tvNombre.text = serie.nombre
            tvOverview.text = serie.descrip
            tvScore.text = serie.rating.toString()
            adapterTemporadas.submitList(serie.temporadas)
        }


    }

    private fun loadRecyclerView(idSerie: Int) {

        viewModel.controlaEventos(SeriesContract.SeriesEvent.PedirSerie(idSerie))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.pbLoading.visibility =
                        if (value.loading) View.VISIBLE else View.GONE
                    value.serie?.let {
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

        adapterTemporadas = DetallesSeriesAdapter(binding.root.context,
            object : DetallesSeriesAdapter.AccionesSerie {
                override fun verCapitulos(temporada: Temporada) {
                    irFragmentDetallesTemproada(idSerie, temporada.numTemporada)
                }
            })

        binding.rvTemporadas.adapter = adapterTemporadas
        binding.rvTemporadas.layoutManager = LinearLayoutManager(binding.root.context)


    }

    private fun irFragmentDetallesTemproada(idSerie: Int, numTemporada: Int) {
        val action =
            DetallesSeriesFragmentDirections.actionDetallesSeriesFragmentToDetallesTemporada(
                idSerie,
                numTemporada
            )
        findNavController().navigate(action)
    }

}