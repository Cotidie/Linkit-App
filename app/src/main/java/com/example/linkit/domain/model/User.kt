package com.example.linkit.domain.model

/** 로그인한 유저를 표현하는 클래스 */
class User(
    val token: String,
    val id: Long,
    val email: String,
    val name: String,
) {
    companion object {
        val GUEST = User("", Long.MIN_VALUE, "Guest", "Guest")
    }

    fun isGuest() : Boolean = (id == Long.MIN_VALUE)

    override fun equals(other: Any?): Boolean {
        if (other !is User) return false
        return id == other.id
    }

    override fun toString(): String {
        return "User(token: $token, id: $id, email: $email, name: $name"
    }

    override fun hashCode(): Int {
        var result = token.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}