package model

import com.google.gson.annotations.SerializedName

data class AnswerInfo(
    @SerializedName("question") val question: String?,
    @SerializedName("answer") val answer: String?
//    @SerializedName("user_email") val userEmail: String?,
//    @SerializedName("user_age") val userAge: String?,
//    @SerializedName("user_uid") val userUid: String?
)