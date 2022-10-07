package com.furkanbalci.travelguide.data.models.article


import com.furkanbalci.travelguide.di.DetailObject
import com.squareup.moshi.Json

data class Article(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "name_language_info")
    val nameLanguageInfo: Any?,
    @Json(name = "score")
    val score: Double,
    @Json(name = "snippet")
    val snippet: String,
    @Json(name = "snippet_language_info")
    val snippetLanguageInfo: Any?,
    @Json(name = "structured_content")
    val structuredContent: StructuredContent,
    @Json(name = "structured_content_language_info")
    val structuredContentLanguageInfo: Any?
): DetailObject {
    data class StructuredContent(
        @Json(name = "attribution")
        val attribution: List<Attribution>,
        @Json(name = "images")
        val images: List<Image>,
        @Json(name = "sections")
        val sections: List<Section>
    ) {
        data class Attribution(
            @Json(name = "source_id")
            val sourceId: String,
            @Json(name = "url")
            val url: String
        )

        data class Image(
            @Json(name = "attribution")
            val attribution: Attribution,
            @Json(name = "caption")
            val caption: String,
            @Json(name = "sizes")
            val sizes: Sizes,
            @Json(name = "source_id")
            val sourceId: String,
            @Json(name = "source_url")
            val sourceUrl: String
        ) {
            data class Attribution(
                @Json(name = "attribution_link")
                val attributionLink: String,
                @Json(name = "attribution_text")
                val attributionText: String,
                @Json(name = "format")
                val format: String,
                @Json(name = "license_link")
                val licenseLink: String?,
                @Json(name = "license_text")
                val licenseText: String
            )

            data class Sizes(
                @Json(name = "medium")
                val medium: Medium,
                @Json(name = "original")
                val original: Original,
                @Json(name = "thumbnail")
                val thumbnail: Thumbnail
            ) {
                data class Medium(
                    @Json(name = "bytes")
                    val bytes: Int,
                    @Json(name = "format")
                    val format: String,
                    @Json(name = "height")
                    val height: Int,
                    @Json(name = "url")
                    val url: String,
                    @Json(name = "width")
                    val width: Int
                )

                data class Original(
                    @Json(name = "bytes")
                    val bytes: Int,
                    @Json(name = "format")
                    val format: String,
                    @Json(name = "height")
                    val height: Int,
                    @Json(name = "url")
                    val url: String,
                    @Json(name = "width")
                    val width: Int
                )

                data class Thumbnail(
                    @Json(name = "bytes")
                    val bytes: Int,
                    @Json(name = "format")
                    val format: String,
                    @Json(name = "height")
                    val height: Int,
                    @Json(name = "url")
                    val url: String,
                    @Json(name = "width")
                    val width: Int
                )
            }
        }

        data class Section(
            @Json(name = "body")
            val body: String,
            @Json(name = "body_images")
            val bodyImages: List<Int>,
            @Json(name = "coordinates")
            val coordinates: Any?,
            @Json(name = "labels")
            val labels: List<Any>,
            @Json(name = "object_id")
            val objectId: Any?,
            @Json(name = "object_type")
            val objectType: Any?,
            @Json(name = "sections")
            val sections: List<Section>,
            @Json(name = "summary")
            val summary: String?,
            @Json(name = "title")
            val title: String,
            @Json(name = "topics")
            val topics: List<Any>
        ) {
            data class Section(
                @Json(name = "body")
                val body: String,
                @Json(name = "body_images")
                val bodyImages: List<Int>,
                @Json(name = "coordinates")
                val coordinates: Any?,
                @Json(name = "labels")
                val labels: List<Any>,
                @Json(name = "object_id")
                val objectId: Any?,
                @Json(name = "object_type")
                val objectType: Any?,
                @Json(name = "sections")
                val sections: List<Any>,
                @Json(name = "summary")
                val summary: String?,
                @Json(name = "title")
                val title: String,
                @Json(name = "topics")
                val topics: List<Any>
            )
        }
    }

    override fun mainImageUrl(): String {
        return this.structuredContent.images.firstOrNull()?.sizes?.medium?.url ?: ""
    }

    override fun name(): String {
        return this.name
    }

    override fun description(): String {
        return this.structuredContent.sections.firstOrNull()?.body ?: ""
    }

    override fun miniDescription(): String {
        return this.score.toString() + " Score"
    }

    override fun getOtherImages(): List<String> {
        return this.structuredContent.images.map { it.sizes.medium.url }
    }
}