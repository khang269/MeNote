package com.critisys.menote.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.critisys.menote.domain.model.MeNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(entities = [MeNote::class], version = 1, exportSchema = false)
public abstract class MeNoteDatabase: RoomDatabase(){
    abstract fun MeNoteDao(): MeNoteDao

    companion object{
        val DATABASE_NAME: String = "me_note_database"
    }

    suspend fun populateInitialData(){
        if(MeNoteDao().count() == 0){
            MeNoteDao().insert(MeNote(
                id = 1,
                title = "Hello",
                content = "Default Note"
            ))
        }
    }
}

class NoteDBCallback (
    private val provider: Provider<MeNoteDao>
) : RoomDatabase.Callback() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        val note = MeNote(title = "LastName", content = "Default Note")
        provider.get().insert(note)
    }
}