package org.svbeck.auburn.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.svbeck.auburn.models.MovieData

class MovieListViewModel : ViewModel() {
    private var exercises: MutableLiveData<Array<MovieData>>? = null

    fun getUpcommingMovies(): MutableLiveData<Array<MovieData>> {
        return exercises ?: MutableLiveData()
    }


}
