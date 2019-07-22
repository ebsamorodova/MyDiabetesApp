package ru.hse.cs.ai.kate.app

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.DatePickerDialog
import android.text.InputType
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    lateinit var picker: DatePickerDialog
    lateinit var eTextBirth: EditText
    lateinit var eSurname: EditText
    lateinit var eName: EditText
    lateinit var xorButton: FloatingActionButton

    private var needShow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        eName = view.findViewById(R.id.editName)
        eName.inputType = InputType.TYPE_NULL
        eSurname = view.findViewById(R.id.editSurname)
        eSurname.inputType = InputType.TYPE_NULL
        eTextBirth = view.findViewById(R.id.editBirth) as EditText
        eTextBirth.inputType = InputType.TYPE_NULL
        xorButton = view.findViewById(R.id.fab_menu_edit)

        eTextBirth.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                activity,
                AlertDialog.THEME_HOLO_LIGHT, // TODO: change to smth else
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    eTextBirth.setText(
                        dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                    )
                },
                year,
                month,
                day
            )
            if (needShow) { // предполагается ввод
                picker.show()
            }
        }

        xorButton.setOnClickListener {
            if (needShow) { // ввод был разрешен
                eName.inputType = InputType.TYPE_NULL
                eSurname.inputType = InputType.TYPE_NULL
                xorButton.setImageResource(android.R.drawable.ic_menu_edit)
            } else {
                eName.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                eSurname.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                xorButton.setImageResource(android.R.drawable.ic_menu_save)
            }
            needShow = needShow.not()
        }
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
