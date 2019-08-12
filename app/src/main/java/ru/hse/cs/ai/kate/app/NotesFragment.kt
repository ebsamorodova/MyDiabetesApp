package ru.hse.cs.ai.kate.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_notes.view.*
import kotlinx.android.synthetic.main.note_layout.view.*
import java.text.DateFormat
import java.util.*
import kotlin.random.Random
import androidx.recyclerview.widget.DividerItemDecoration


class NotesFragment : Fragment() {
    lateinit var newNote: FloatingActionButton
    lateinit var deleteNotes: FloatingActionButton
    lateinit var list: RecyclerView
    lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_notes, container, false).also { view ->
        notesAdapter = NotesAdapter()
        newNote = view.fab_new_note.apply {
            setOnClickListener {
                // Navigation.findNavController(it).navigate(R.id.action_notesFragment_to_addNoteFragment, null)
                view.notes_list.scrollToPosition(0)
                val nextNote = Note(
                    Calendar.getInstance().time,
                    Random.nextDouble(),
                    Random.nextDouble(),
                    Random.nextDouble(),
                    Random.nextDouble()
                )

                notesAdapter.addNote(nextNote)
            }
        }

        deleteNotes = view.fab_clear_base.apply {
            setOnClickListener {
                notesAdapter.deleteAllNotes()
            }
        }

        list = view.notes_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = notesAdapter
            val itemDivider = DividerItemDecoration(context, RecyclerView.VERTICAL)
            addItemDecoration(itemDivider)


            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    when {
                        dy > 0 && newNote.isOrWillBeShown -> newNote.hide()
                        dy < 0 && newNote.isOrWillBeHidden -> newNote.show()
                    }
                }
            })
        }

    }
}

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return Holder(textView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val element = data[position]
        holder.item.textDate.text = "Запись от ${DateFormat.getDateTimeInstance().format(element.date)}"
        holder.item.textGlucose.text = "%.1f".format(element.glucose)
        holder.item.textBreadUnits.text = "%.1f".format(element.breadUnits)
        holder.item.textInsulinBasal.text = "%.1f".format(element.insulinBasal)
        holder.item.textInsulinBolus.text = "%.1f".format(element.insulinBolus)
    }

    class Holder(val item: View) : RecyclerView.ViewHolder(item)

    val databaseDao = App.instance.database.notesDao()
    val data = databaseDao.getAll().toMutableList()

    fun addNote(note: Note) {
        data.add(0, note)
        databaseDao.insert(note)
        notifyItemRangeInserted(0, 1)
    }

    fun deleteAllNotes() {
        val amount = itemCount
        databaseDao.deleteAll()
        data.clear()
        notifyItemRangeRemoved(0, amount)
    }
}
