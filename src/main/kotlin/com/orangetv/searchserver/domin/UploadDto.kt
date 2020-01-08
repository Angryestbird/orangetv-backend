package com.orangetv.searchserver.domin

import com.orangetv.searchserver.entity.Movie

data class UploadDto(val host: String, val movies: List<Movie>)