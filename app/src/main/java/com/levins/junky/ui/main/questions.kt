package com.levins.junky.ui.main

//data class questions(val questions: ArrayList<Question>)
//
//data class Question(
//    val question: String,
//    val type: String,
//    val obligatory: String,
//    val answers: List<String>
//)
class questions : ArrayList<questionsItem>()

data class questionsItem(
    val answers: List<String>,
    val obligatory: String,
    val question: String,
    val type: String
)

//class questions : ArrayList<questionsItem>()
//
//data class questionsItem(
//    val answers: List<String>,
//    val obligatory: String,
//    val question: String,
//    val type: String
//)