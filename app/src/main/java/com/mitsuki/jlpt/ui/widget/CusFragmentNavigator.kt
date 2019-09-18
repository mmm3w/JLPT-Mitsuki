package com.mitsuki.jlpt.ui.widget

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import java.util.*

@Navigator.Name("cus_fragment")
class CusFragmentNavigator(
    private val context: Context, private val manager: FragmentManager, private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {

        try{
            if (manager.isStateSaved) {
                Log.i("FragmentNavigator", "Ignoring navigate() call: FragmentManager has already" + " saved its state")
                return null
            }

            var className = destination.className
            if (className[0] == '.') {
                className = context.packageName + className
            }

            val ft = manager.beginTransaction()

            var enterAnim = navOptions?.enterAnim ?: -1
            var exitAnim = navOptions?.exitAnim ?: -1
            var popEnterAnim = navOptions?.popEnterAnim ?: -1
            var popExitAnim = navOptions?.popExitAnim ?: -1
            if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
                enterAnim = if (enterAnim != -1) enterAnim else 0
                exitAnim = if (exitAnim != -1) exitAnim else 0
                popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
                popExitAnim = if (popExitAnim != -1) popExitAnim else 0
                ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            }

            /** replace ***************************************************************************/
            val tag =  destination.id.toString()
            var frag = manager.findFragmentByTag(tag)
            if (frag == null){
                frag = instantiateFragment(context, manager, className, args)
                frag.arguments = args
                ft.add(containerId, frag, tag)
            }else{
                ft.show(frag)
            }
            /**************************************************************************************/

            /** del *******************************************************************************/
//            ft.replace(containerId, frag)
            /**************************************************************************************/
            ft.setPrimaryNavigationFragment(frag)
            /** add *******************************************************************************/
            val mBackStackField = FragmentNavigator::class.java.getDeclaredField("mBackStack")
            mBackStackField.isAccessible = true
            val mBackStack: ArrayDeque<Int> = mBackStackField.get(this) as ArrayDeque<Int>
            /**************************************************************************************/

            @IdRes val destId = destination.id
            val initialNavigation = mBackStack.isEmpty()
            // TODO Build first class singleTop behavior for fragments
            val isSingleTopReplacement =
                (navOptions != null && !initialNavigation && navOptions.shouldLaunchSingleTop() && mBackStack.peekLast() == destId)

            val isAdded: Boolean
            when {
                initialNavigation -> isAdded = true
                isSingleTopReplacement -> {
                    // Single Top means we only want one instance on the back stack
                    if (mBackStack.size > 1) {
                        // If the Fragment to be replaced is on the FragmentManager's
                        // back stack, a simple replace() isn't enough so we
                        // remove it from the back stack and put our replacement
                        // on the back stack in its place
                        manager.popBackStack(
                            generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                        ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
                    }
                    isAdded = false
                }
                else -> {
                    ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
                    isAdded = true
                }
            }
            if (navigatorExtras is Extras) {
                val extras = navigatorExtras as Extras?
                for ((key, value) in extras!!.sharedElements) {
                    ft.addSharedElement(key, value)
                }
            }
            ft.setReorderingAllowed(true)
            ft.commit()
            // The commit succeeded, update our view of the world
            return if (isAdded) {
                mBackStack.add(destId)
                /** add ***************************************************************************/
                mBackStackField.isAccessible = false
                /**********************************************************************************/
                destination
            } else {
                /** add ***************************************************************************/
                mBackStackField.isAccessible = false
                /**********************************************************************************/
                null
            }
        }catch (e:Exception){
            return super.navigate(destination, args, navOptions, navigatorExtras)
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}

class CusNavHostFragment : NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return CusFragmentNavigator(requireContext(), childFragmentManager, id)
    }
}