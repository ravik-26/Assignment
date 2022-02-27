package com.miletrips.assignment.di

import android.app.Application
import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    fun provideExoPlayer(
        context: Context,
        loadControl: LoadControl,
        cacheDataSourceFactory: CacheDataSource.Factory
    ): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .setLoadControl(loadControl)
            .build()
    }

    @Singleton
    @Provides
    fun providesLoadControl(): LoadControl {
        return DefaultLoadControl.Builder()
            .setBufferDurationsMs(15000, 50000, 1500, 2500)
            .build()
    }

    @Singleton
    @Provides
    fun provideProgressiveMediaSourceFactory(dataSourceFactory: CacheDataSource.Factory): ProgressiveMediaSource.Factory {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
    }

    @Singleton
    @Provides
    fun provideCacheDataSourceFactory(
        cache: SimpleCache,
        defaultDataSourceFactory: DefaultDataSource.Factory
    ): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(defaultDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    @Singleton
    @Provides
    fun provideDefaultDataSourceFactory(
        context: Context,
        factory: HttpDataSource.Factory
    ): DefaultDataSource.Factory {
        return DefaultDataSource.Factory(context, factory)
    }

    @Singleton
    @Provides
    fun provideHttpDataSourceFactory(): HttpDataSource.Factory {
        return DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)
    }

    @Singleton
    @Provides
    fun provideSimpleCache(
        context: Context,
        evictor: LeastRecentlyUsedCacheEvictor,
        databaseProvider: DatabaseProvider
    ): SimpleCache {
        return SimpleCache(
            File(context.cacheDir, "exoplayer-media"),
            evictor, databaseProvider
        )
    }

    @Singleton
    @Provides
    fun provideLeastRecentlyUsedCacheEvictor(): LeastRecentlyUsedCacheEvictor {
        return LeastRecentlyUsedCacheEvictor(200 * 1024 * 1024)
    }

    @Singleton
    @Provides
    fun provideDatabaseProvider(context: Context): DatabaseProvider {
        return StandaloneDatabaseProvider(context)
    }
}