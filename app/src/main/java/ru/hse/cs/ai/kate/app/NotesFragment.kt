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
                notesAdapter.data.add(
                    Note(
                        Calendar.getInstance().time,
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    )
                )
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

data class Note(
    val date: Date,
    val glucose: Double,
    val insulinBasal: Double,
    val insulinBolus: Double,
    val breadUnits: Double
)

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return Holder(textView)
    }

    override fun getItemCount(): Int = data.size()

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val element = data[position]
        holder.item.textDate.text = "Запись от ${DateFormat.getDateTimeInstance().format(element.date)}"
    }

    class Holder(val item: View) : RecyclerView.ViewHolder(item)

    val data = SortedList<Note>(Note::class.java, object : SortedList.Callback<Note>() {
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem

        override fun areItemsTheSame(item1: Note, item2: Note): Boolean = item1.date == item2.date

        override fun onMoved(fromPosition: Int, toPosition: Int) = notifyItemMoved(fromPosition, toPosition)

        override fun onChanged(position: Int, count: Int) = notifyItemChanged(position, count)

        override fun onInserted(position: Int, count: Int) = notifyItemRangeInserted(position, count)

        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)

        override fun compare(o1: Note, o2: Note) = -o1.date.compareTo(o2.date)

    })
}


object NotesStorage {
}