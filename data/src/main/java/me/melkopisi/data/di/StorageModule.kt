package me.melkopisi.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.melkopisi.data.local.GithubReposDatabase
import javax.inject.Singleton

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

  @Singleton
  @Provides fun provideGithubReposDatabase(
    @ApplicationContext context: Context
  ) = Room.databaseBuilder(context, GithubReposDatabase::class.java, "githubRepos.db")
    .fallbackToDestructiveMigration()
    .build()
}