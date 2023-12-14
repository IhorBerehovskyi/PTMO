package com.myproj.studhelp


data class Student @JvmOverloads constructor(
    var surname: String = "",
    var name: String = "",
    var fatherName: String = "",
    var APKS: Int = 0,
    var MZOSZ: Int = 0,
    var MOS: Int = 0,
    var PZIR: Int = 0,
    var PTMO: Int = 0,
    var TSD: Int = 0,
    var rating: Double = 0.0
)
