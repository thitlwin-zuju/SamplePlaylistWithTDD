package petros.efthymiou.groovy.playlist

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val okHttpClient = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {
    @Provides
    fun providePlaylistApi(retrofit: Retrofit): PlaylistAPI = retrofit.create(PlaylistAPI::class.java)

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.58:3001")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}