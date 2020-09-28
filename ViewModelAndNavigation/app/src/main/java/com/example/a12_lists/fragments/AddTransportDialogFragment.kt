package com.example.a12_lists.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.a12_lists.R
import com.example.a12_lists.model.TransportType
import com.example.a12_lists.utils.showToast
import com.google.android.material.textfield.TextInputLayout

class AddTransportDialogFragment : DialogFragment(

) {
    private lateinit var transportTypeTextInput: TextInputLayout
    private lateinit var typeRelatedParamTextInput: TextInputLayout
    private lateinit var transportNameTextInput: TextInputLayout
    private lateinit var maxSpeedTextInput: TextInputLayout
    private var transportTypeSelected: TransportType? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_add_transport, null)

            transportTypeTextInput = view.findViewById(R.id.transporttype_dropdown)
            typeRelatedParamTextInput = view.findViewById(R.id.typeRelatedParamTF)
            transportNameTextInput = view.findViewById(R.id.transportNameTF)
            maxSpeedTextInput = view.findViewById(R.id.maxSpeedTF)

            builder.setView(view)
                // Add action buttons
                .setPositiveButton(getString(R.string.ok), null)
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }

            val items =
                listOf(getString(TransportType.EARTH.desc), getString(
                    TransportType.AIR.desc), getString(
                    TransportType.WATER.desc))
            val adapter =
                ArrayAdapter(requireContext(),
                    R.layout.item_transporttype_dropdown, items)
            val dropDownList = (transportTypeTextInput.editText as? AutoCompleteTextView)
            dropDownList?.setAdapter(adapter)
            dropDownList?.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    when (position) {
                        0 -> typeRelatedParamTextInput.hint =
                            getString(R.string.door_count)
                        1 -> typeRelatedParamTextInput.hint =
                            getString(R.string.engine_count)
                        2 -> typeRelatedParamTextInput.hint =
                            getString(R.string.displacement)
                    }
                    transportTypeSelected = TransportType.values()[position]
                    typeRelatedParamTextInput.visibility = View.VISIBLE
                }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog?
        if (dialog != null) {
            val positiveButton: Button = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                var wantToCloseDialog = true
                val transportName = transportNameTextInput.editText?.text.toString()
                val maxSpeed = maxSpeedTextInput.editText?.text.toString().toIntOrNull()
                val typeRelatedParam =
                    typeRelatedParamTextInput.editText?.text.toString().toIntOrNull()

                if (transportName.trim().isEmpty()) {
                    showToast(
                        getString(R.string.name_is_empty),
                        context
                    )
                    wantToCloseDialog = false
                }
                if (transportTypeSelected == null) {
                    showToast(
                        getString(R.string.transport_type_is_empty),
                        context
                    )
                    wantToCloseDialog = false
                }
                if (maxSpeed == null) {
                    showToast(
                        getString(R.string.max_speed_is_empty),
                        context
                    )
                    wantToCloseDialog = false
                }
                if (transportTypeSelected != null && typeRelatedParam == null) {
                    showToast(
                        getString(R.string.last_param_is_empty),
                        context
                    )
                    wantToCloseDialog = false
                }


                if (wantToCloseDialog) {

                    (parentFragment as TransportListFragment).addTransport(
                        transportName,
                        transportTypeSelected!!,
                        maxSpeed!!,
                        typeRelatedParam!!
                    )

                    dialog.dismiss()
                }
            }
        }
    }

}