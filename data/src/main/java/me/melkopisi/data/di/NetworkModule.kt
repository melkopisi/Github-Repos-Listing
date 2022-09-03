package me.melkopisi.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.melkopisi.data.network.NetworkInterceptor
import me.melkopisi.data.network.api.GithubReposApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Singleton
  @Provides fun provideGson(): Gson = Gson()

  @Singleton
  @Provides fun provideRetrofit(
    client: OkHttpClient,
    gson: Gson,
  ): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/users/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build()

  @Singleton
  @Provides
  fun providesOkHttpClient(
    networkInterceptor: NetworkInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .writeTimeout(60, TimeUnit.SECONDS)
      .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      })
      .addInterceptor(networkInterceptor)
      .build()
  }

  @Singleton
  @Provides
  fun providesGithubApi(retrofit: Retrofit): GithubReposApi {
    return retrofit.create(GithubReposApi::class.java)
  }
}