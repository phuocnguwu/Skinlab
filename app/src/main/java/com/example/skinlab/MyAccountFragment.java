package com.example.skinlab;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.skinlab.databinding.FragmentMyAccountBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    FragmentMyAccountBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean OpenCam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                if (OpenCam) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    binding.imvavatar.setImageBitmap(photo);
                } else {
                    Uri selectedPhotoUri = result.getData().getData();
                    binding.imvavatar.setImageURI(selectedPhotoUri);
                }

            }

        });
    }

    private void addEvents() {
        binding.moronghoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Myaccount_Profile.class);
                startActivity(intent);
            }
        });
        binding.morongdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Donhang_dathang.class);
                startActivity(intent);

            }
        });
        binding.morongdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Myaccount_Diachi.class);
                startActivity(intent);

            }
        });
        binding.morongcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.morongdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btneditavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();

            }

            private void showBottomSheet() {
                Dialog dialog = new Dialog(requireActivity());
                dialog.setContentView(R.layout.bottomsheet_dialog);

                LinearLayout bsCam = dialog.findViewById(R.id.bsCam);
                bsCam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenCam = true;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        activityResultLauncher.launch(intent);
                        dialog.dismiss();
                    }
                });

                LinearLayout bsGal = dialog.findViewById(R.id.bsGallery);
                bsGal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activityResultLauncher.launch(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

    }
}