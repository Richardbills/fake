package com.example.fitnesscoach.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnesscoach.room.entity.Records
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(records: Records)

    @Query("SELECT * FROM records WHERE uid =:uid ORDER BY id DESC")
    fun getRecords(uid:String): Flow<List<Records>>

    @Query("SELECT * FROM records LIMIT 1")
    fun readAllRecords(): Flow<List<Records>>

    @Query("DELETE FROM records")
    suspend fun truncate()
}











//@Dao
//interface StoreDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(store: Store)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun update(store: Store)
//
//    @Delete
//    suspend fun delete(store: Store)
//
//    @Query("SELECT * FROM stores")
//    fun getAllItems(): Flow<Store>
//
//    @Query("SELECT * FROM stores WHERE store_id =:storeId")
//    fun getItem(storeId:Int): Flow<Store>
//}
//@Dao
//interface ListDao{
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertShoppingList(shoppingList: ShoppingList)
//
//    @Query("""
//        SELECT * FROM items AS I INNER JOIN shopping_list AS S
//        ON I.listIdFk = S.list_id INNER JOIN stores AS ST
//        ON I.storeIdFk = ST.store_id
//    """)
//    fun getItemsWithStoreAndList(): Flow<List<ItemsWithStoreAndList>>
//
//    @Query("""
//        SELECT * FROM items AS I INNER JOIN shopping_list AS S
//        ON I.listIdFk = S.list_id INNER JOIN stores AS ST
//        ON I.storeIdFk = ST.store_id WHERE ST.listIdFk =:listId
//    """)
//    fun getItemsWithStoreAndListFilteredById(listId:Int): Flow<List<ItemsWithStoreAndList>>
//
//    @Query("""
//        SELECT * FROM items AS I INNER JOIN shopping_list AS S
//        ON I.listIdFk = S.list_id INNER JOIN stores AS ST
//        ON I.storeIdFk = ST.store_id WHERE I.item_id =:itemId
//    """)
//    fun getItemWithStoreAndList(itemId:Int): Flow<ItemsWithStoreAndList>
//
//}
//@Dao
//data class ItemsWithStoreAndList(
//    @Embedded val item : Records,
//    @Embedded val shoppingList: ShoppingList,
//    @Embedded val store: Store
//)