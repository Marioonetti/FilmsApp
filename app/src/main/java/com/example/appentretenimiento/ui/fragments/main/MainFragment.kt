package com.example.appentretenimiento.ui.fragments.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appentretenimiento.databinding.FragmentMainBinding
import com.example.appentretenimiento.domain.model.SeriePelicula
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapterPeliculas: MainAdapter
    private lateinit var mainAdapterSeries: MainAdapter


    private val viewModel: TrendingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
    }


    private fun configRecyclerView() {
        viewModel.controlaEventos(FragmentMainContract.EventMainFragment.PedirDatos(binding.root.context))


        mainAdapterPeliculas =
            MainAdapter(binding.root.context, object : MainAdapter.Interacciones {
                override fun muestraInfo(seriePelicula: SeriePelicula) {
                    irDetallesPelis(seriePelicula.id)
                }
            })

        mainAdapterSeries = MainAdapter(binding.root.context, object : MainAdapter.Interacciones {
            override fun muestraInfo(seriePelicula: SeriePelicula) {
                idDetallesSeries(seriePelicula.id)
            }
        })

        binding.rvPeliculas.adapter = mainAdapterPeliculas
        binding.rvPeliculas.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)


        binding.rvSeries.adapter = mainAdapterSeries
        binding.rvSeries.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)





        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.pbLoading.visibility = if (value.loading) View.VISIBLE else View.GONE

                    mainAdapterPeliculas.submitList(value.peliculas)
                    mainAdapterSeries.submitList(value.series)


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


    private fun irDetallesPelis(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetallesFragment(id)
        findNavController().navigate(action)
    }

    private fun idDetallesSeries(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetallesSeriesFragment(id)
        findNavController().navigate(action)
    }


}