package com.ubaya.todoapp.model

import androidx.room.*

@Dao
interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)

    @Query("Select * from todo order by priority desc")
    suspend fun selectAllTodo(): List<Todo>

    @Query("Select * from todo where uuid = :id")
    suspend fun selectTodo(id:Int):Todo

    @Query("update todo set title = :title,notes = :notes,priority = :priority where uuid = :id")
    suspend fun update(id:Int,title:String,notes:String,priority:Int)
    @Delete
    suspend fun deleteTodo(todo:Todo)
}