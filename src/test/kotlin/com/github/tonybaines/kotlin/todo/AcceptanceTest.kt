package com.github.tonybaines.kotlin.todo

import com.github.tonybaines.java.todo.ToDo
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmptyString
import org.junit.jupiter.api.Test


object AcceptanceTest {
    val TODO_APP = ToDo()

    @Test
    fun `show all todo items`(){
        assertThat(TODO_APP.read("list"), isEmptyString)
    }

    @Test
    fun `add a todo item`(){
    }

    @Test
    fun `remove a todo item by ID`(){
    }

    @Test
    fun `mark an item as complete`(){
    }

    @Test
    fun `mark an item as high importance`(){
    }

    @Test
    fun `rejects a task with no description`(){
    }

}