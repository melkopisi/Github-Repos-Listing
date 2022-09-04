package me.melkopisi.githubreposlisting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.melkopisi.githubreposlisting.di.qualifiers.IOThread
import me.melkopisi.githubreposlisting.di.qualifiers.MainThread
import javax.inject.Singleton

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
class SchedulersModule {

  @Singleton
  @Provides
  @IOThread fun provideIOExecutor(): Scheduler = Schedulers.io()

  @Singleton
  @Provides
  @MainThread fun provideMainThreadExecutor(): Scheduler = AndroidSchedulers.mainThread()
}
