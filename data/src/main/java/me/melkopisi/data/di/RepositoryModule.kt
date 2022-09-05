package me.melkopisi.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.melkopisi.data.repository.GithubReposRepositoryImpl
import me.melkopisi.domain.repository.GithubReposRepository

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  abstract fun bindGithubReposRepository(githubReposRepositoryImpl: GithubReposRepositoryImpl): GithubReposRepository
}