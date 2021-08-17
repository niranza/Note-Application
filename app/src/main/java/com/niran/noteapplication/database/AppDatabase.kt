package com.niran.noteapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.niran.noteapplication.database.daos.NoteDao
import com.niran.noteapplication.database.models.Note
import com.niran.noteapplication.utils.Constants.Companion.WELCOME_NOTE_TEXT
import com.niran.noteapplication.utils.Constants.Companion.WELCOME_NOTE_TITLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    class RoomCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.noteDao())
                }
            }
        }

        private suspend fun populateDatabase(dao: NoteDao) {

            dao.deleteAllNotes()

            val welcomeNote = Note(
                noteTitle = WELCOME_NOTE_TITLE,
                noteText = WELCOME_NOTE_TEXT,
            )

            dao.insertNote(welcomeNote)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .addCallback(RoomCallBack(scope))
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }

}