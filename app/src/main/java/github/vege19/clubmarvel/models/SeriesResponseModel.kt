package github.vege19.clubmarvel.models

import java.io.Serializable

data class SeriesResponseModel(val available: Int = 0,
                               val collectionURI: String = "",
                               val items: List<RegularModel>): Serializable