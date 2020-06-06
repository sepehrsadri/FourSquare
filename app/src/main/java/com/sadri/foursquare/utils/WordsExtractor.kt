package com.sadri.foursquare.utils

/**
 * Created by Sepehr Sadri on 6/5/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
object WordsExtractor {
    private val enWords = listOf(
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z',
        ' '
    )

    fun extractEnglishDigits(text: String?): String {
        if (text.isNullOrEmpty()) return " "

        val stringBuilder = StringBuilder()

        val temp = text as String

        temp.forEachIndexed { index, char ->
            if (enWords.contains(char.toLowerCase())) {
                stringBuilder.append(text[index])
            }
        }

        val result = stringBuilder.toString()
        val nonSpaceResult = result.replace(" ", "")

        return if (nonSpaceResult.isEmpty()) text else result.trimStart().trimEnd()
    }
}