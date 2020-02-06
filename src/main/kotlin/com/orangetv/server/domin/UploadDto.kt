package com.orangetv.server.domin

import com.orangetv.server.entity.Movie

data class UploadDto(val hostUrl: String, val movies: List<Movie>)