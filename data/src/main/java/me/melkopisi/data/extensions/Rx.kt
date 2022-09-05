package me.melkopisi.data.extensions

import io.reactivex.Single
import me.melkopisi.domain.exceptions.DataRetrievingFailException
import me.melkopisi.domain.exceptions.NoDataException
import retrofit2.Response

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

fun <T> Single<Response<T>>.parseResponse(): Single<T> {
  return flatMap { response ->
    if (response.isSuccessful) {
      if (response.body() != null) Single.just(response.body()) else Single.error(NoDataException())
    } else {
      Single.error(DataRetrievingFailException())
    }
  }
}