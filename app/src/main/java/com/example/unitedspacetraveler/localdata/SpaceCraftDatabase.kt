package com.example.unitedspacetraveler.localdata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Database(
    entities = [SpaceCraftDatabaseModel::class],
    version = 1,
    exportSchema = false
)
abstract class SpaceCraftDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): SpaceCraftDatabase {
            return Room.databaseBuilder(context, SpaceCraftDatabase::class.java, "space_craft.db")
                .allowMainThreadQueries().build()
        }
    }

    abstract fun spaceCraftDao(): SpaceCraftDao
}

@Dao
interface SpaceCraftDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: SpaceCraftDatabaseModel)

    @Query("DELETE FROM spaceCraft")
    suspend fun deleteCraft()

    @Query("SELECT * FROM spaceCraft ORDER BY id LIMIT 1")
    fun getSpaceCraft(): LiveData<SpaceCraftDatabaseModel?>

}

@Entity(tableName = "spaceCraft")
data class SpaceCraftDatabaseModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    var spaceSuitNumber: Int,
    var universalSpaceTime: Int,
    var strengthTime: Int?,
)