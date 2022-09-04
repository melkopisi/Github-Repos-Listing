package me.melkopisi.data.network

import android.content.Context
import android.os.SystemClock
import dagger.hilt.android.qualifiers.ApplicationContext
import me.melkopisi.data.extensions.isInternetAvailable
import me.melkopisi.domain.NetworkNotAvailableException
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class NetworkInterceptor @Inject constructor(
  @ApplicationContext private val context: Context
) : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Chain): Response {
    val request = chain.request()
    if (context.isInternetAvailable().not()) throw NetworkNotAvailableException()
    SystemClock.sleep(1000L)
    return chain.proceed(request)
  }
}