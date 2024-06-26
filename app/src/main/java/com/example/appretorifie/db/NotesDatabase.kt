package com.example.appretorifie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.TypeConverters
import com.example.appretorifie.db.model.Note
import com.example.appretorifie.db.Converters

@Database(entities = [(Note::class)],
                    version = 1,
                    exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotesDatabase: RoomDatabase(){

    abstract fun notesDao(): NotesDao

    companion object{
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
    

}

