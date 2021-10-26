package com.example.stackoverflow.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

sealed class WidgetData

@Keep
data class Question(

    @ColumnInfo(name = "accepted_answer_id")
    @SerializedName("accepted_answer_id")
    val acceptedAnswerId: Int,

    @ColumnInfo(name = "answer_count")
    @SerializedName("answer_count")
    val answerCount: Int,

    @ColumnInfo(name = "content_license")
    @SerializedName("content_license")
    val contentLicense: String,

    @ColumnInfo(name = "creation_date")
    @SerializedName("creation_date")
    val creationDate: Long,

    @ColumnInfo(name = "is_answered")
    @SerializedName("is_answered")
    val isAnswered: Boolean,

    @ColumnInfo(name = "last_activity_date")
    @SerializedName("last_activity_date")
    val lastActivityDate: Int,

    @ColumnInfo(name = "last_edit_date")
    @SerializedName("last_edit_date")
    val lastEditDate: Int,

    @ColumnInfo(name = "link")
    @SerializedName("link")
    val link: String,

    @ColumnInfo(name = "owner")
    @SerializedName("owner")
    val owner: Owner,

    @PrimaryKey
    @ColumnInfo(name = "question_id")
    @SerializedName("question_id")
    val questionId: Int,

    @ColumnInfo(name = "score")
    @SerializedName("score")
    val score: Int,

    @ColumnInfo(name = "tags")
    @SerializedName("tags")
    val tags: List<String>,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "view_count")
    @SerializedName("view_count")
    val viewCount: Int,

) : WidgetData()

@Keep
data class Banner(
    var bannerImage: String?,
    var position: Int = -1
) : WidgetData()

