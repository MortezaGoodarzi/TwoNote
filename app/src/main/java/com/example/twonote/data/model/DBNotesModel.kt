package com.example.twonote.data.model

data class DBNotesModel(
    var id : Int,
    var title : String,
    var details : String,
    var deleteState : String,
    var date : String
)