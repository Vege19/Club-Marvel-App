package github.vege19.clubmarvel.models

import java.io.Serializable

data class StoriesResponseModel(val available: Int = 0,
                                val collectionUri: String = "",
                                val items: List<StoryModel> = arrayListOf()): Serializable