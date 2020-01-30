package com.orangetv.server.domin

import com.orangetv.server.entity.Movie

data class UploadDto(val host: String, val movies: List<Movie>)