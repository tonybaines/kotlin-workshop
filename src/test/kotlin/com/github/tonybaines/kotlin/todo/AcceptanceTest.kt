package com.github.tonybaines.kotlin.todo

import com.github.tonybaines.java.todo.ToDo
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmptyString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


object AcceptanceTest {
    lateinit var toDo : ToDo
    @BeforeEach
    fun setUp() {
        toDo = ToDo()
    }

    @Test
    fun `show all todo items`(){
        assertThat(toDo.read("list"), isEmptyString)
    }

    @Test
    fun `add a todo item`(){
        assertThat(toDo.read("add"), equalTo(ToDo.Prompts.ADD_PROMPT))
        assertThat(toDo.read("First thing to do"), equalTo("[1] First thing to do"))
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