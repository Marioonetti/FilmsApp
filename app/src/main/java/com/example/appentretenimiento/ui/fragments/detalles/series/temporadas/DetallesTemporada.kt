package com.example.appentretenimiento.ui.fragments.detalles.series.temporadas

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
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.appentretenimiento.databinding.FragmentDetallesTemporadaBinding
import com.example.appentretenimiento.domain.model.Temporada
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetallesTemporada : Fragment() {

    private var _binding: FragmentDetallesTemporadaBinding? = null
    private val binding get() = _binding!!

    private val args: DetallesTemporadaArgs by navArgs()

    private val viewModel: TemporadasViewModel by viewModels()

    private lateinit var adapterCaps: TemporadasAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallesTemporadaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        loadRecycler()

    }

    private fun loadData() {

        val idSerie = args.idSerie
        val numTemporada = args.numTemporada

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                        value ->
                    binding.pbLoading.visibility =
                        if (value.loading) View.VISIBLE else View.GONE
                    value.temporada?.let {
                        pintar(it)
                        adapterCaps.submitList(it.episodios)
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

        viewModel.controlaEventos(TemporadasContract.TemporadaEvent.PedirTemporada(idSerie, numTemporada))



    }

    private fun loadRecycler() {

        adapterCaps = TemporadasAdapter(binding.root.context)
        binding.rvEpisodios.adapter = adapterCaps
        binding.rvEpisodios.layoutManager = LinearLayoutManager(binding.root.context)


    }

    private fun pintar(temporada : Temporada){
        with(binding) {
            imgPoster.load(temporada.poster)
            tvNombre.text = temporada.nombre
            tvOverview.text = temporada.resumen
            adapterCaps.submitList(temporada.episodios)



        }
    }


}