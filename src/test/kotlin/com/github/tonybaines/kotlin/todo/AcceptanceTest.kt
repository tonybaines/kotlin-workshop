package com.github.tonybaines.kotlin.todo

import com.github.tonybaines.java.todo.ToDo
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmptyString
import com.natpryce.hamkrest.startsWith
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
        assertThat(toDo.read("add"), equalTo(ToDo.Prompts.ADD))
        assertThat(toDo.read("First thing to do"), startsWith(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), equalTo("[1] First thing to do"))
    }

    @Test
    fun `remove a todo item by ID`(){
        val id = Fixture.givenAnItemIsAdded("Todo 1")

        assertThat(toDo.read("delete"), equalTo(ToDo.Prompts.DELETE))
        assertThat(toDo.read(id), equalTo(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), isEmptyString)
    }

    @Test
    fun `mark an item as complete`(){
        val id = Fixture.givenAnItemIsAdded("Todo 2")

        assertThat(toDo.read("complete"), equalTo(ToDo.Prompts.COMPLETE))
        assertThat(toDo.read(id), equalTo(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), containsSubstring(String.format("[%s] Todo 2 (DONE)", id)))
    }

    @Test
    fun `mark an item as high importance`(){
    }

    @Test
    fun `rejects a task with no description`(){
    }

    @Test
    fun `rejects an unknown command`(){
    }

    object Fixture {
        fun givenAnItemIsAdded(description: String): String {
            toDo.read("add")
            val result = toDo.read(description)
            assertThat(result, startsWith(ToDo.Messages.SUCCESS))

            // Extract the ID of the newly-created item
            val (id) = """.*\[([0-9]+)].*""".toRegex().find(result)!!.destructured
            return id
        }
    }

}
