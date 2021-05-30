package com.example.unitedspacetraveler.localdata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Database(
    entities = [StationsDatabaseModel::class],
    version = 2,
    exportSchema = false
)
abstract class StationsDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): StationsDatabase {
            return Room.databaseBuilder(context, StationsDatabase::class.java, "stations.db")
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun stationsDao(): StationsDao
}

@Dao
interface StationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: List<StationsDatabaseModel>)

    @Query("DELETE FROM stations")
    suspend fun deleteStations()

    @Query("SELECT * FROM stations")
    fun getStations(): LiveData<List<StationsDatabaseModel>?>

    @Query("SELECT * FROM stations WHERE isFavorite = :isFavorite")
    fun getFavorites(isFavorite: Boolean = true): LiveData<List<StationsDatabaseModel>?>

    @Query("UPDATE stations SET isFavorite= :isFavorite WHERE id = :id")
    fun setFavorite(isFavorite: Boolean, id: Int)

}

@Entity(tableName = "stations")
data class StationsDatabaseModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    var coordinateX: Double,
    var coordinateY: Double,
    var capacity: Int,
    var stock: Int,
    var need: Int,
    var isFavorite: Boolean
)