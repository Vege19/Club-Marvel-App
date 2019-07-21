package github.vege19.clubmarvel.models

import java.io.Serializable

data class SeriesModel(val id: Int = 0,
                       val title: String = "",
                       val description: String = "",
                       val startYear: Int = 0,
                       val endYear: Int = 0,
                       val rating: String = "",
                       val modified: String = "",
                       val thumbnail: ThumbnailModel? = null,
                       val creators: CreatorsResponseModel? = null,
                       val characters: CharactersResponseModel? = null,
                       val stories: StoriesResponseModel? = null,
                       val comics: ComicsResponseModel? = null): Serializable