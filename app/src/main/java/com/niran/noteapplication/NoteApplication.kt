package com.niran.noteapplication

import android.app.Application
import com.niran.noteapplication.database.AppDatabase
import com.niran.noteapplication.repositories.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this, applicationScope) }

    val noteRepository: NoteRepository by lazy { NoteRepository(database.noteDao()) }


}