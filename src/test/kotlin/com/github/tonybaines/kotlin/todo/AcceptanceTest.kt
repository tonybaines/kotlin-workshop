package com.github.tonybaines.kotlin.todo

import com.github.tonybaines.java.todo.ToDo
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmptyString
import com.natpryce.hamkrest.startsWith
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


object AcceptanceTest {
    lateinit var toDo: ToDo
    @BeforeEach
    fun setUp() {
        toDo = ToDo()
    }

    @Test
    fun `show all todo items`() {
        assertThat(toDo.read("list"), isEmptyString)
    }

    @Test
    fun `add a todo item`() {
        assertThat(toDo.read("add"), equalTo(ToDo.Prompts.ADD))
        assertThat(toDo.read("First thing to do"), startsWith(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), equalTo("[1] First thing to do"))
    }

    @Test
    fun `rejects a task with no description`() {
        assertThat(toDo.read("add"), equalTo(ToDo.Prompts.ADD))
        assertThat(toDo.read(""), startsWith(ToDo.Messages.FAILURE))
    }

    @Test
    fun `remove a todo item by ID`() {
        val id = Fixture.givenAnItemIsAdded("Todo 1")

        assertThat(toDo.read("delete"), equalTo(ToDo.Prompts.DELETE))
        assertThat(toDo.read(id), equalTo(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), isEmptyString)
    }

    @Test
    fun `rejects a request to remove an unknown item`() {
        toDo.read("delete")
        assertThat(toDo.read("-99"), startsWith(ToDo.Messages.FAILURE))
    }

    @Test
    fun `mark an item as complete`() {
        val id = Fixture.givenAnItemIsAdded("Todo 2")

        assertThat(toDo.read("complete"), equalTo(ToDo.Prompts.COMPLETE))
        assertThat(toDo.read(id), equalTo(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), containsSubstring(String.format("[%s] Todo 2 (DONE)", id)))
    }

    @Test
    fun `reword an item description`() {
        val id = Fixture.givenAnItemIsAdded("Todo 2")

        assertThat(toDo.read("reword"), equalTo(ToDo.Prompts.REWORD))
        assertThat(toDo.read(id), equalTo(ToDo.Prompts.REWORD_TEXT))
        assertThat(toDo.read("Todo 3 (was Todo 2)"), equalTo(ToDo.Messages.SUCCESS))
        assertThat(toDo.read("list"), containsSubstring(String.format("[%s] Todo 3 (was Todo 2)", id)))
    }

    @Test
    fun `rejects a request to update an unknown item`() {
        toDo.read("complete")
        assertThat(toDo.read("-99"), startsWith(ToDo.Messages.FAILURE))
    }

    @Test
    fun `mark an item as high importance`() {
    }

    @Test
    fun `rejects an unknown command`() {
        assertThrows<UnsupportedOperationException> {
            toDo.read("UNEXPECTED-ITEM")
        }
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
