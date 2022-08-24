package com.itrocket.union.inventoryContainer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.R
import com.itrocket.union.addFragment
import com.itrocket.union.inventory.presentation.store.InventoryArguments
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_ARGUMENT
import com.itrocket.union.inventoryContainer.InventoryContainerModule.INVENTORYCONTAINER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventoryContainer.domain.InventoryContainerType
import com.itrocket.union.inventoryContainer.presentation.store.InventoryContainerStore
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragment
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragment.Companion.INVENTORY_CREATE_ARGUMENTS
import com.itrocket.union.replaceFragment
import com.itrocket.union.utils.fragment.ChildBackPressedHandler

class InventoryContainerComposeFragment :
    BaseComposeFragment<InventoryContainerStore.Intent, InventoryContainerStore.State, InventoryContainerStore.Label>(
        INVENTORYCONTAINER_VIEW_MODEL_QUALIFIER
    ), ChildBackPressedHandler, InventoryCreateClickHandler {
    override val navArgs by navArgs<InventoryContainerComposeFragmentArgs>()

    private var inventoryContainer: ConstraintLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_inventory_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val composeView = ComposeView(requireContext())
        inventoryContainer = view.findViewById(R.id.inventoryContainer)
        super.onViewCreated(composeView, savedInstanceState)
    }


    override fun handleLabel(label: InventoryContainerStore.Label) {
        when (label) {
            InventoryContainerStore.Label.GoBack -> findNavController().navigateUp()
            is InventoryContainerStore.Label.ShowInventoryCreate -> replaceFragment(
                getInventoryCreateFragment(label.inventoryCreateArguments)
            )
        }
    }

    private fun initStartFragment() {
        val argument = navArgs.inventoryContainerArguments
        val initialFragment = when (argument.type) {
            InventoryContainerType.INVENTORY_CREATE -> getInventoryCreateFragment(
                InventoryCreateArguments(requireNotNull(argument.inventoryCreateDomain))
            )
            InventoryContainerType.INVENTORY -> getInventoryFragment(InventoryArguments(argument.inventoryCreateDomain))
        }
        childFragmentManager.addFragment(
            R.id.inventoryContainer,
            initialFragment,
        )
    }

    private fun getInventoryCreateFragment(argument: InventoryCreateArguments): Fragment {
        return InventoryCreateComposeFragment().apply {
            arguments = bundleOf(
                INVENTORY_CREATE_ARGUMENTS to argument
            )
        }
    }

    private fun getInventoryFragment(argument: InventoryArguments): Fragment {
        return InventoryComposeFragment().apply {
            arguments = bundleOf(
                INVENTORY_ARGUMENT to argument
            )
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.replaceFragment(R.id.inventoryContainer, fragment)
    }

    override fun onChildBackPressed() {
        accept(InventoryContainerStore.Intent.OnBackClicked)
    }

    override fun onInventoryCreateClicked(inventoryCreate: InventoryCreateDomain) {
        replaceFragment(
            getInventoryCreateFragment(
                InventoryCreateArguments(inventoryCreate)
            )
        )
    }

    override fun renderState(
        state: InventoryContainerStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        //no-op
    }
}