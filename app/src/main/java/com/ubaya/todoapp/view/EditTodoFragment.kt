package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.todoapp.R
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTodoFragment : Fragment() {
    private lateinit var viewModel:DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        textJudul.text = "Edit Todo"
        buttonAdd.text = "Save Changes"

        buttonAdd.setOnClickListener {
            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
            viewModel.update(uuid,editTitle.text.toString(),editNotes.text.toString(),radio.tag.toString().toInt())
            Toast.makeText(view.context,"todo update",Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner){
            editTitle.setText(it.title)
            editNotes.setText(it.notes)
            when(it.priority){
                1->radioLow.isChecked = true
                2->radioMedium.isChecked = true
                else->radioHigh.isChecked = true
            }
        }
    }
}