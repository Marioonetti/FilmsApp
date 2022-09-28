package com.example.appentretenimiento.ui.fragments.favoritos

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appentretenimiento.R
import com.example.appentretenimiento.databinding.FragmentFavoritosBinding
import com.example.appentretenimiento.domain.model.SeriePelicula
import com.example.appentretenimiento.ui.main.MainActivity
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritosFragment : Fragment() {


    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private lateinit var favsAdapter: FavoritosAdapter

    private val viewModel: FavoritosViewModel by viewModels()

    private lateinit var actionMode: ActionMode


    private val callback by lazy {
        configContextBar()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()


    }

    private fun loadData() {

        favsAdapter =
            FavoritosAdapter(binding.root.context, object : FavoritosAdapter.AccionesFavs {
                override fun changeBarNumber() {
                    actionMode.title = viewModel.numSeleccionados().toString()
                }

                override fun eliminarFav(seriePelicula: SeriePelicula) {
                    viewModel.controlaEventos(
                        FavoritosContract.FavoritosEvent.EliminarFav(
                            seriePelicula
                        )
                    )
                }

                override fun noHaySeleccionados(): Boolean = viewModel.haySeleccionados()

                override fun deselectItem(seriePelicula: SeriePelicula) {
                    viewModel.eliminarSeleccionado(seriePelicula)
                }

                override fun selectItem(seriePelicula: SeriePelicula) {
                    viewModel.addSeleccionado(seriePelicula)
                }

                override fun endSelectMode() {
                    viewModel.vaciarLista()
                    actionMode.finish()
                }

                override fun muestraInfo(seriePelicula: SeriePelicula) {
                    if (seriePelicula.tipo == Variables.SERIE_TYPE) {
                        irDetallesSeries(seriePelicula.id)
                    } else {
                        irDetallesPelis(seriePelicula.id)
                    }
                }


                override fun isItemSelected(seriePelicula: SeriePelicula): Boolean =
                    viewModel.isSeleccionado(seriePelicula)

                override fun startSelectMode() {
                    (requireActivity() as MainActivity)
                        .startSupportActionMode(callback)?.let {
                            actionMode = it
                        }
                }
            })

        binding.rvFavs.adapter = favsAdapter
        binding.rvFavs.layoutManager = LinearLayoutManager(
            binding.root.context, LinearLayoutManager.VERTICAL, false
        )

        val touchHelper = ItemTouchHelper(favsAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvFavs)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    favsAdapter.submitList(value.listaFavs)
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

        viewModel.controlaEventos(FavoritosContract.FavoritosEvent.PedirDatos)

    }


    private fun irDetallesPelis(id: Int) {
        val action = FavoritosFragmentDirections.actionFavoritosFragmentToFavLocalFilms(id)
        findNavController().navigate(action)
    }

    private fun irDetallesSeries(id: Int) {
        val action = FavoritosFragmentDirections.actionFavoritosFragmentToDetallesSeriesFragment(id)
        findNavController().navigate(action)
    }


    private fun configContextBar() =
        object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.btnBorrar -> {
                        viewModel.controlaEventos(FavoritosContract.FavoritosEvent.EliminarAllFavs)
                        actionMode.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                favsAdapter.resetSelectMode()

            }

        }


}