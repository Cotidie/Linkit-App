package com.example.linkit._enums

/** 일반모드, 편집모드, 이동모드 등 현재 UI의 모드를 정의한다. */
enum class UIMode {
    NORMAL, EDIT_LINK, EDIT_FOLDER, ADD_LINK, ADD_FOLDER, ADD_TAG, RENAME_FOLDER, ALL_LINK;

    fun isEditMode() = when (this) {
        EDIT_FOLDER, EDIT_LINK, RENAME_FOLDER, ADD_TAG -> true
        else -> false
    }
}