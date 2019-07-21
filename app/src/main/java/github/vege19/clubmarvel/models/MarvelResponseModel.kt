package github.vege19.clubmarvel.models

data class MarvelResponseModel(val code: Int = 0,
                               val status: String = "",
                               val copyright: String = "",
                               val attributionText: String = "",
                               val attributionHTML: String = "",
                               val etag: String = "",
                               val data: MarvelComicsDataResponseModel? = null)