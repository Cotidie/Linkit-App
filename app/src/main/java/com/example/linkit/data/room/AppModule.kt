package com.example.linkit.data.room

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.dao.LinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesLinkDao(linkitDatabase: LinkItDatabase) : LinkDao
        = linkitDatabase.linkDao()

    @Singleton
    @Provides
    fun providesFolderDao(linkitDatabase: LinkItDatabase) : FolderDao
        = linkitDatabase.folderDao()

    @Singleton
    @Provides
    fun providesRoomCallback() : RoomDatabase.Callback
        = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                val folder = ContentValues()
                folder.put("name", "무제폴더")
                db.insert("folderTable", SQLiteDatabase.CONFLICT_IGNORE, folder)
            }
        }


    @Singleton
    @Provides
    fun provideLinkItDatabase(
        @ApplicationContext context: Context,
        callback: RoomDatabase.Callback
    ) : LinkItDatabase
        = Room.databaseBuilder(
        context,
        LinkItDatabase::class.java,
        "linkit_db")
        // 스키마를 업데이트하면 기존 데이터를 비운다.
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()
}