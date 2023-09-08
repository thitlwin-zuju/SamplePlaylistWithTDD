package petros.efthymiou.groovy.detail

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class PlaylistDetailModule {
    @Provides
    fun providePlaylistDetailApi(retrofit: Retrofit): PlaylistDetailAPI = retrofit.create(
        PlaylistDetailAPI::class.java)
}