package com.example.appentretenimiento.ui.fragments.busqueda

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appentretenimiento.databinding.FragmentSeacrhBinding
import com.example.appentretenimiento.domain.model.SeriePeliculaActor
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSeacrhBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeacrhBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRecyclerView()
        configSearch()

    }


    private fun configSearch() {

        binding.svAll.requestFocus()
        binding.svAll.requestFocusFromTouch()

        binding.svAll.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view?.let {
                    showInputMethod(it)
                }
            }
        }

        binding.svAll.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != Variables.STRING_VACIO) {
                    viewModel.controlaEventos(BusquedaContract.EventBusqueda.Buscar(newText))
                }

                return false
            }


        })


    }

    private fun loadRecyclerView() {

        searchAdapter = SearchAdapter(binding.root.context, object : SearchAdapter.Acciones {
            override fun muestraInfo(seriePeliculaActor: SeriePeliculaActor) {
                irFragmentDetalles(seriePeliculaActor)
            }
        })

        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.layoutManager = LinearLayoutManager(binding.root.context)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->

                    binding.pbLoading.visibility =
                        if (value.loading) View.VISIBLE else View.GONE

                    searchAdapter.submitList(value.listaBusqueda)
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


    private fun irFragmentDetalles(seriePeliculaActor: SeriePeliculaActor) {

        val action: NavDirections = when (seriePeliculaActor.tipo) {
            Variables.SERIE_TYPE -> SearchFragmentDirections.actionSeacrhFragmentToDetallesSeriesFragment2(
                seriePeliculaActor.id
            )
            Variables.PELICULA_TYPE -> SearchFragmentDirections.actionSeacrhFragmentToDetallesFragment(
                seriePeliculaActor.id
            )
            else -> {
                SearchFragmentDirections.actionSeacrhFragmentToDetallesActores(seriePeliculaActor.id)
            }

        }


        findNavController().navigate(action)


    }

    private fun showInputMethod(view: View) {
        val inputManager =
            binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, 0)
    }

}