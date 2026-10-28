package com.bolsojovem.pixcontrol.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.data.model.Transaction
import com.bolsojovem.pixcontrol.data.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val amount: Double,
    val category: String,
    val type: String,
    val timestampEpochMilli: Long,
    val pixKey: String?,
    val note: String?
) {
    fun toDomain(): Transaction {
        val cat = try {
            ExpenseCategory.valueOf(category)
        } catch (e: Exception) {
            ExpenseCategory.FOOD
        }
        val tType = try {
            TransactionType.valueOf(type)
        } catch (e: Exception) {
            TransactionType.EXPENSE
        }
        val dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestampEpochMilli),
            ZoneId.systemDefault()
        )
        return Transaction(
            id = id,
            title = title,
            amount = amount,
            category = cat,
            type = tType,
            timestamp = dateTime,
            pixKey = pixKey,
            note = note
        )
    }

    companion object {
        fun fromDomain(transaction: Transaction): TransactionEntity {
            val epoch = transaction.timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            return TransactionEntity(
                id = transaction.id,
                title = transaction.title,
                amount = transaction.amount,
                category = transaction.category.name,
                type = transaction.type.name,
                timestampEpochMilli = epoch,
                pixKey = transaction.pixKey,
                note = transaction.note
            )
        }
    }
}

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestampEpochMilli DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: String)

    @Query("DELETE FROM transactions")
    suspend fun clearAll()
}
