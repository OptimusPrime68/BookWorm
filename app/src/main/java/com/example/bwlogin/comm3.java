package com.example.bwlogin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bwlogin.Adapters.MessageAdapter;
import com.example.bwlogin.Models.AllMethods;
import com.example.bwlogin.Models.Message;
import com.example.bwlogin.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class comm3 extends Fragment implements OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String name;
    public String uid;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messagedb;
    MessageAdapter messageAdapter;
    User u;
    List<Message> messages;

    RecyclerView rvMessage;
    EditText etMessage;
    ImageButton imgButton;


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
        // Inflate the layout for this fragment

        View r=inflater.inflate(R.layout.fragment_comm3, container, false);
        getActivity().setTitle("Community Chat") ;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        imgButton = r.findViewById(R.id.btnsend);
        rvMessage = r.findViewById(R.id.rvMessage);
        etMessage = r.findViewById(R.id.etMessage);
        imgButton.setOnClickListener(this);
        messages = new ArrayList<>();
        //init();
        return r;
    }

    /*private  void  init(){





    }*/

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(etMessage.getText().toString())){
            Message message = new Message(etMessage.getText().toString(),name);
            etMessage.setText("");
            messagedb.push().setValue(message);
        }
        else{
            Toast.makeText(getActivity(),"You cannot sen blank messages",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        final FirebaseUser currentUser = auth.getCurrentUser();
        name =currentUser.getDisplayName();
        uid = currentUser.getUid();
        //Toast.makeText(getActivity(),uid,Toast.LENGTH_LONG).show();

        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //u = dataSnapshot.getValue(User.class);
                //u.setUid("id");
                AllMethods.name = name;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messagedb = database.getReference("messages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                messages.add(message);
                displayMessages(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for (Message m : messages){
                    if(m.getKey().equals(message.getKey())){
                        newMessages.add(message);
                    }
                    else
                    {
                        newMessages.add(m);
                    }
                }

                messages = newMessages;
                displayMessages(messages);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                List<Message> newMessages = new ArrayList<Message>();

                for(Message m:messages)
                {
                    if (!m.getKey().equals(message.getKey())){
                        newMessages.add(m);
                    }
                }

                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}

    @Override
    public void onResume(){
        super.onResume();
        messages = new ArrayList<>();
    }

    private void displayMessages(List<Message> messages) {
        rvMessage.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        messageAdapter = new MessageAdapter(this.getActivity(),messages,messagedb);
        rvMessage.setAdapter(messageAdapter);

    }


}
