package com.example.stackoverflow.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = "questions_table")
data class QuestionListResponse(

    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0,

    @ColumnInfo(name = "has_more")
    @SerializedName("has_more")
    var hasMore: Boolean,

    @ColumnInfo(name = "items")
    @SerializedName("items")
    var items: List<Question>?,

    @ColumnInfo(name = "quota_max")
    @SerializedName("quota_max")
    var quotaMax: Int,

    @ColumnInfo(name = "quota_remaining")
    @SerializedName("quota_remaining")
    var quotaRemaining: Int
)