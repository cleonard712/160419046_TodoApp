package com.ubaya.todoapp.model

import androidx.room.*

@Dao
interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)

    @Query("Select * from todo where is_done = 0 order by priority desc")
    suspend fun selectAllTodo(): List<Todo>

    @Query("Select * from todo where uuid = :id")
    suspend fun selectTodo(id:Int):Todo

    @Query("update todo set title = :title,notes = :notes,priority = :priority where uuid = :id")
    suspend fun update(id:Int,title:String,notes:String,priority:Int)

    @Query("update todo set is_done = 1 where uuid = :id")
    suspend fun update(id:Int)  // menggunakan int karena jika menggunakan bollean nanti berubah jadi tinyint
    @Delete
    suspend fun deleteTodo(todo:Todo)
}