package com.example.finalwmp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class EnrollmentSummaryActivity extends AppCompatActivity {

    private ListView listViewSelectedSubjects;
    private TextView textViewHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_summary);

        listViewSelectedSubjects = findViewById(R.id.listViewSelectedSubjects);
        textViewHeader = findViewById(R.id.selectedSubjectsHeader);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        EnrolmentManager em = new EnrolmentManager();

        em.getUserEnrollments(sharedPrefManager.getEmail()).thenAccept(subjects -> {
            if (subjects != null && !subjects.isEmpty()) {
                ArrayList<String> subjectDetails = new ArrayList<>();
                for (Subject subject : subjects) {
                    subjectDetails.add(subject.getSubjectName() + " - Credits: " + subject.getCredits());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectDetails);
                listViewSelectedSubjects.setAdapter(adapter);

                textViewHeader.setText("Selected Subjects:");
            } else {
                textViewHeader.setText("No subjects selected.");
            }
        });
    }
}
