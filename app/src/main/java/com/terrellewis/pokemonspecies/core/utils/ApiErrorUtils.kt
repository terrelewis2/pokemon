package com.terrellewis.pokemonspecies.core.utils

object ApiErrorUtils {
    fun getErrorMessage(exception: Throwable?): String {
        return when (exception) {
            is java.net.UnknownHostException -> "Please check your internet connection"
            else -> "Something went wrong"
        }
    }
}