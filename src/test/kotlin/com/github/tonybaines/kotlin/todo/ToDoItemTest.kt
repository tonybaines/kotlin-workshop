package com.github.tonybaines.kotlin.todo;

import com.github.tonybaines.java.todo.ToDoList
import com.github.tonybaines.java.todo.ToDoList.ToDoItem
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test


class ToDoItemTest {
    private val TEST_ID = ToDoList.Id.from("-99");
    @Test
    fun `can be copied with modified description`() {
        val original = ToDoItem(TEST_ID, "Test 1")
        assertThat(
                original.copy().withDescription("Test 2").done(),
                equalTo(ToDoItem(TEST_ID, "Test 2"))
        );
    }

    @Test
    fun `can be copied with modified completion status`() {
        val original = ToDoItem(TEST_ID, "Test 1")
        assertThat(
                original.copy().withCompletionStatus(true).done(),
                equalTo(ToDoItem(TEST_ID, "Test 1", true))
        );
    }

}