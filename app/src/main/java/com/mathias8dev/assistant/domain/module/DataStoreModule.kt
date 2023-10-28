package com.mathias8dev.assistant.domain.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.ExperimentalMultiProcessDataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import com.mathias8dev.assistant.domain.storage.UserChatHistory
import com.mathias8dev.assistant.domain.storage.UserChatHistorySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @OptIn(ExperimentalMultiProcessDataStore::class)
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<UserChatHistory> {
        return MultiProcessDataStoreFactory.create(
            serializer = UserChatHistorySerializer(),
            produceFile = {
                File(
                    context.cacheDir,
                    UserChatHistorySerializer.filename
                )
            }
        )
    }
}