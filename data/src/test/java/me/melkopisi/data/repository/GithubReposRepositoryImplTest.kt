package me.melkopisi.data.repository

import com.google.gson.Gson
import me.melkopisi.data.MockResponseFileReader
import me.melkopisi.data.local.GithubReposLocalDataSource
import me.melkopisi.data.network.api.GithubReposApi
import me.melkopisi.data.remote.GithubReposRemoteDataSource
import me.melkopisi.domain.repository.GithubReposRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
 * Authored by Kopisi on 04 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class GithubReposRepositoryImplTest {
  private val server: MockWebServer = MockWebServer()
  private val MOCK_WEBSERVER_PORT = 8000
  lateinit var githubReposApi: GithubReposApi
  lateinit var remoteDataSource: GithubReposRemoteDataSource
  lateinit var jsonRepository: GithubReposRepository
  @Mock lateinit var localDataSource: GithubReposLocalDataSource

  @Before
  fun setup() {
    server.start(MOCK_WEBSERVER_PORT)

    githubReposApi = Retrofit.Builder()
      .baseUrl(server.url("/"))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(Gson()))
      .build()
      .create(GithubReposApi::class.java)
    remoteDataSource = GithubReposRemoteDataSource(githubReposApi)
    jsonRepository = GithubReposRepositoryImpl(remoteDataSource, localDataSource)
  }

  @After
  fun shutdown() {
    server.shutdown()
  }

  @Test
  fun `getReposList API parse success`() {
    server.apply {
      enqueue(
        MockResponse().setBody(
          MockResponseFileReader(
            "GetReposListSuccess.json"
          ).content
        )
      )
    }
    jsonRepository.getRepos(1)
      .test()
      .awaitDone(3, TimeUnit.SECONDS)
      .assertComplete()
      .assertValue { it[0].name == "abs.io" }
  }

  @Test
  fun `getReposList API parse fail`() {
    server.apply {
      enqueue(
        MockResponse().setBody(
          MockResponseFileReader(
            "GetReposListFail.json"
          ).content
        )
      )
    }
    jsonRepository.getRepos(1)
      .test()
      .awaitDone(3, TimeUnit.SECONDS)
      .assertNoValues()
  }
}