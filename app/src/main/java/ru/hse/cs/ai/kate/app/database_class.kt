package ru.hse.cs.ai.kate.app

import androidx.room.*
import java.util.*

@Entity
data class Note(
    @PrimaryKey val date: Date,
    @ColumnInfo(name = "glucose") val glucose: Double,
    @ColumnInfo(name = "insulin_basal") val insulinBasal: Double,
    @ColumnInfo(name = "insulin_bolus") val insulinBolus: Double,
    @ColumnInfo(name = "bread_units") val breadUnits: Double
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAll(): List<Note>

    @Query("DELETE FROM note")
    fun deleteAll()

    @Insert
    fun insertAll(vararg notes: Note)

    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(notes: Note)
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

@Database(entities = arrayOf(Note::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}

