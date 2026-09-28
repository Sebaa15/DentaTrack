package org.ucb.dentatrack.Movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Movies.domain.usecase.GetMoviesUseCase

class CatalogViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _effect =
        MutableSharedFlow<CatalogEffect>()

    val effect =
        _effect.asSharedFlow()

    private val _state =
        MutableStateFlow(CatalogState())

    val state =
        _state.asStateFlow()

    init {
        emitEvent(CatalogEvent.LoadMovie)
    }

    fun emitEvent(event: CatalogEvent) {

        when (event) {

            CatalogEvent.LoadMovie -> {
                loadMovies()
            }

            CatalogEvent.Retry -> {
                loadMovies()
            }
        }
    }

    private fun loadMovies() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val result =
                getMoviesUseCase.invoke()

            result.fold(

                onSuccess = { movies ->

                    _state.update {
                        it.copy(
                            movies = movies,
                            isLoading = false,
                            error = null
                        )
                    }
                },

                onFailure = {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "No se pudieron cargar las películas"
                        )
                    }

                    emitEffect(
                        CatalogEffect.ShowMessage(
                            "Error al cargar las películas"
                        )
                    )
                }
            )
        }
    }

    private fun emitEffect(
        effect: CatalogEffect
    ) {

        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}