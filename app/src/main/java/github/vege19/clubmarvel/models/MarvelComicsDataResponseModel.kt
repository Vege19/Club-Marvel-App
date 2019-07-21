package github.vege19.clubmarvel.models

data class MarvelComicsDataResponseModel(val offset: Int = 0,
                                         val limit: Int = 0,
                                         val total: Int = 0,
                                         val count: Int = 0,
                                         val results: List<ComicModel> = arrayListOf())