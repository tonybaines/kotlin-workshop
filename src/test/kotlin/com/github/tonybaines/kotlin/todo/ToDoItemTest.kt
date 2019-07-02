package com.github.tonybaines.kotlin.todo;

import com.github.tonybaines.java.todo.ToDoList.ToDoItem
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test


class ToDoItemTest {
    @Test
    fun `can be copied with modified description`() {
        val original = ToDoItem(-99, "Test 1")
        assertThat(
                original.copy().withDescription("Test 2").done(),
                equalTo(ToDoItem(-99, "Test 2"))
        );
    }

    @Test
    fun `can be copied with modified completion status`() {
        val original = ToDoItem(-99, "Test 1")
        assertThat(
                original.copy().withCompletionStatus(true).done(),
                equalTo(ToDoItem("-99", "Test 1", true))
        );
    }

}