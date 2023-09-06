package com.critisys.menote.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.critisys.menote.data.database.MeNoteDao
import com.critisys.menote.data.database.MeNoteDatabase
import com.critisys.menote.data.database.NoteDBCallback
import com.critisys.menote.data.repository.MeNoteDatabaseRepository
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.repository.MeNoteRepository
import com.critisys.menote.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application, provider: Provider<MeNoteDao>): MeNoteDatabase{
        val noteDb : MeNoteDatabase =
            Room
                .databaseBuilder(
                    app.applicationContext,
                    MeNoteDatabase::class.java,
                    MeNoteDatabase.DATABASE_NAME)
                .addCallback(NoteDBCallback(provider = provider))
            .build()
        return noteDb
    }

    @Provides
    @Singleton
    fun provideMeNoteDao(db: MeNoteDatabase): MeNoteDao{
        return db.MeNoteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dao: MeNoteDao): MeNoteRepository{
        return MeNoteDatabaseRepository(dao)
    }

    @Provides
    @Singleton
    fun provideNoteUsecase(repository: MeNoteRepository): HomeNotesUseCase{
        return HomeNotesUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideDetailNoteUsecase(repository: MeNoteRepository): DetailNoteUseCase{
        return DetailNoteUseCase(
            getNote = GetNote(repository),
            addNote = AddNote(repository)
        )
    }
}