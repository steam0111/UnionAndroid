package com.itrocket.union

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replaceFragment(containerId: Int, fragment: Fragment) {
    beginTransaction()
        .replace(
            containerId,
            fragment,
            fragment::class.java.name
        )
        .addToBackStack(fragment::class.java.name)
        .commit()
}

fun FragmentManager.addFragment(containerId: Int, fragment: Fragment) {
    beginTransaction()
        .add(
            containerId,
            fragment,
            fragment::class.java.name
        )
        .addToBackStack(fragment::class.java.name)
        .commit()
}