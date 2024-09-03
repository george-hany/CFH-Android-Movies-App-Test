package com.core.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import timber.log.Timber

fun navigate(
    view: View,
    id: Int,
    args: Bundle?,
    navOptions: NavOptions?,
    extras: Navigator.Extras?,
) {
    try {
        Navigation.findNavController(view).navigate(id, args, navOptions, extras)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun transition(
    action: NavDirections,
    v: View,
) {
    try {
        v.findNavController().navigate(action)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun navigate(
    navController: NavController,
    id: Int,
    args: Bundle?,
    navOptions: NavOptions?,
    extras: Navigator.Extras?,
) {
    try {
        navController.navigate(id, args, navOptions, extras)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(
    navController: NavController,
    destinationId: Int?,
) {
    try {
        if (destinationId != null) {
            navController.popBackStack(destinationId, false)
        } else {
            navController.popBackStack()
        }
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(
    view: View,
    destinationId: Int?,
) {
    try {
        if (destinationId != null) {
            Navigation.findNavController(view).popBackStack(destinationId, false)
        } else {
            Navigation.findNavController(view).popBackStack()
        }
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(
    fragment: Fragment,
    destinationId: Int?,
) {
    try {
        if (destinationId != null) {
            NavHostFragment.findNavController(fragment).popBackStack(destinationId, false)
        } else {
            NavHostFragment.findNavController(fragment).popBackStack()
        }
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(fragment: Fragment) {
    try {
        NavHostFragment.findNavController(fragment).navigateUp()
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}
