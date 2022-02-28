package com.apptive.linkit.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector

data class IconText(
    val icon: ImageVector? = null,
    val text: String = ""
) {
    fun hasIcon() : Boolean = (icon != null)
    override fun toString(): String {
        return "IconText: ${icon}, ${text}"
    }
}