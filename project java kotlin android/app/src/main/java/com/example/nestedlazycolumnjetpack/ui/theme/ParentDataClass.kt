package com.example.nestedlazycolumnjetpack.ui.theme

import android.icu.text.CaseMap.Title

data class ParentDataClass(
    val title: String,
    val childList: List<ChildDataClass>
)
