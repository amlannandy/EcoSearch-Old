package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.RecordDetailsActivity
import com.aknindustries.ecosearch.databinding.RecordBottomSheetBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.GlideLoader
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RecordBottomSheetFragment(val record: Record): BottomSheetDialogFragment() {

    private var _binding: RecordBottomSheetBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "record_bottom_sheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecordBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        GlideLoader(requireActivity().applicationContext).loadRecordImage(
            record.image,
            binding.recordBottomSheetImage,
        )
        binding.recordBottomSheetTitle.text = record.title
        binding.recordBottomSheetLabel.text = when (record.label) {
            Constants.NULL -> resources.getString(R.string.unknown)
            else -> record.label
        }
        binding.recordBottomSheetBtn.setOnClickListener {
            val intent = Intent(activity, RecordDetailsActivity::class.java)
            intent.putExtra(Constants.RECORD_ID, record.id)
            startActivity(intent)
        }
    }

}