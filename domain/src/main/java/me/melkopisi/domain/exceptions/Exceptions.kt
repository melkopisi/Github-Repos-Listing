package me.melkopisi.domain

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class NetworkNotAvailableException : Throwable("Network is not available.")
class DataRetrievingFailException : Throwable("Error getting data from server.")
class NoDataException : Throwable("No data.")
class NoLocalDataException : Throwable("No local data.")
class GeneralException(msg: String) : Throwable(msg)
