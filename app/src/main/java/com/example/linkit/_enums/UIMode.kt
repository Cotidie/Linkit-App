package com.example.linkit._enums

/** 일반모드, 편집모드, 이동모드 등 현재 UI의 모드를 정의한다. */
enum class UIMode {
    NORMAL, EDIT_LINK, EDIT_FOLDER, ADD_LINK, ADD_FOLDER, RENAME_FOLDER;

    fun isEditMode() = (this == EDIT_LINK || this == EDIT_FOLDER)
}