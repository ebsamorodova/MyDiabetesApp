package ru.hse.cs.ai.kate.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add_note.view.*
import java.util.*
import android.widget.Toast


class AddNoteFragment : Fragment() {
    lateinit var getNewNote: FloatingActionButton
    lateinit var eGlucose: EditText
    lateinit var eBreadUnits: EditText
    lateinit var eInsulinBasal: EditText
    lateinit var eInsulinBolus: EditText
    lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_note, container, false).also { view ->
        eGlucose = view.findViewById(R.id.editGlucose)
        eBreadUnits = view.findViewById(R.id.editBreadUnits)
        eInsulinBasal = view.findViewById(R.id.editInsulinBasal)
        eInsulinBolus = view.findViewById(R.id.editInsulinBolus)

        getNewNote = view.fab_save_note.apply {
            setOnClickListener {
                if (eGlucose.text.isEmpty() ||
                    eBreadUnits.text.isEmpty() ||
                    eInsulinBasal.text.isEmpty() ||
                    eInsulinBolus.text.isEmpty()
                ) {
                    toast = Toast.makeText(
                        activity,
                        "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT
                    )
                    toast.show()
                    return@setOnClickListener
                }

                val newGlucose = eGlucose.text.toString().toDoubleOrNull()
                val newBreadUnits = eBreadUnits.text.toString().toDoubleOrNull()
                val newInsulinBasal = eInsulinBasal.text.toString().toDoubleOrNull()
                val newInsulinBolus = eInsulinBolus.text.toString().toDoubleOrNull()
                if (newGlucose == null || newBreadUnits == null || newInsulinBasal == null || newInsulinBolus == null) {
                    toast = Toast.makeText(
                        activity,
                        "Пожалуйста, введите числа", Toast.LENGTH_SHORT
                    )
                    toast.show()
                    return@setOnClickListener
                }

                val nextNote = Note(
                    Calendar.getInstance().time,
                    newGlucose, newBreadUnits, newInsulinBasal, newInsulinBolus
                )
                val databaseDao = App.instance.database.notesDao()
                databaseDao.insert(nextNote)

                toast = Toast.makeText(
                    activity,
                    "Запись добавлена!", Toast.LENGTH_SHORT
                )
                toast.show()

                eGlucose.text.clear()
                eBreadUnits.text.clear()
                eInsulinBasal.text.clear()
                eInsulinBolus.text.clear()
            }
        }
    }
}

