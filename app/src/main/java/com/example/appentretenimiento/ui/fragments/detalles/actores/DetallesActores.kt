package com.example.appentretenimiento.ui.fragments.detalles.actores

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
import com.example.appentretenimiento.databinding.FragmentDetallesActoresBinding
import com.example.appentretenimiento.domain.model.Actor
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetallesActores : Fragment() {
    private var _binding: FragmentDetallesActoresBinding? = null
    private val binding get() = _binding!!

    private val args: DetallesActoresArgs by navArgs()

    private val viewModel: ActoresViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallesActoresBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()


    }

    private fun loadData() {
        val id = args.id

        binding.ivStar.load(Variables.IMAGE_ESTRELLA)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uierror.collect {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->

                    binding.pbLoading.visibility =
                        if (value.loading) View.VISIBLE else View.GONE

                    value.actor?.let {
                        loadVisual(it)
                    }
                }
            }
        }




        viewModel.controlaEventos(ActoresContract.EventActores.PedirActor(id))

    }


    private fun loadVisual(actor: Actor) {
        with(binding) {
            imgPoster.load(actor.imagen)
            tvNombre.text = actor.nombre
            tvOverview.text = actor.biografia
            tvScore.text = actor.pupularidad.toString()
        }
    }

}