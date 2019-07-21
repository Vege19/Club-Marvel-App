package github.vege19.clubmarvel.models

import java.io.Serializable

data class ComicModel(val id: Int = 0,
                      val title: String = "",
                      val description: String = "",
                      val modified: String = "",
                      val isbn: String = "",
                      val format: String = "",
                      val pageCount: Int = 0,
                      val series: SeriesResponseModel? = null,
                      val dates: List<DateModel> = arrayListOf(),
                      val prices: List<PriceModel> = arrayListOf(),
                      val thumbnail: ThumbnailModel? = null,
                      val creators: CreatorsResponseModel? = null,
                      val characters: CharactersResponseModel? = null,
                      val stories: StoriesResponseModel? = null): Serializable