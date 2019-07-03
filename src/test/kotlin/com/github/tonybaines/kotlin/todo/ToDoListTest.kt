package com.github.tonybaines.kotlin.todo

import com.github.tonybaines.java.todo.ToDoList
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmptyString
import com.natpryce.hamkrest.matches
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ToDoListTest {
    val list = ToDoList()

    @Test
    fun `an item can be added`() {
        assertThat(list.prettyPrint(), isEmptyString)

        val added = list.add("Test 1")
        assertThat(added.id.toString(), matches("""[0-9]+""".toRegex()))
        assertThat(added.description, equalTo("Test 1"))
    }

    @Test
    fun `an item can be removed`() {
        assertThat(list.prettyPrint(), isEmptyString)

        val addedId = list.add("Test 1").id

        list.delete(addedId)

        assertThat(list.prettyPrint(), isEmptyString)
    }

    @Test
    fun `cannot delete an ID that does not exist`() {
        assertThat(list.prettyPrint(), isEmptyString)
        assertThrows<ToDoList.NoSuchItemException> {
            list.delete(ToDoList.Id.nextId())
        }
    }
}