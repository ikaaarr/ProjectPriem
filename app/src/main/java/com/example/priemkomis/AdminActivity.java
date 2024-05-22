package com.example.priemkomis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<StudDannie> zayavkaList;
    private ZayavkaAdapter zayavkaAdapter;
    DatabaseReference databaseReferenceAbiturient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Spinner spin = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.группы, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter1);
        spin.setOnItemSelectedListener(this);
        Spinner spin2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Оригинал, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(this);

        ZayavkaAdapter adapter = new ZayavkaAdapter(zayavkaList);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView);
        recyclerView1.setAdapter(adapter);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Дополнительная сортировка по аттестату (Оригинал/копия)
                String kop = "Копия";
                String orig = "Оригинал";
                String nofing = "Оригинал/Копия аттестата";
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                if (selectedItem.equals(kop)) {
                    Collections.sort(zayavkaList, new Comparator<StudDannie>() {
                        @Override
                        public int compare(StudDannie a, StudDannie b) {
                            // Преобразуйте версию аттестата к числовому значению, например, представив значения как 0 для Копия и 1 для Оригинал
                            int versionA = a.getАттестат().equals("Копия") ? 0 : 1;
                            int versionB = b.getАттестат().equals("Копия") ? 0 : 1;

                            if (versionA == versionB) {
                                // Если версии аттестата совпадают, вернуть результат сортировки по баллам
                                double scoreA = Double.parseDouble(a.getБалл().toString());
                                double scoreB = Double.parseDouble(b.getБалл().toString());
                                return Double.compare(scoreB, scoreA);


                            } else {
                                // Иначе, вернуть результат сортировки по версии аттестата
                                return Integer.compare(versionA, versionB);

                            }

                        }
                    });zayavkaAdapter.notifyDataSetChanged();
                } else if (selectedItem.equals(orig)) {
                    Collections.sort(zayavkaList, new Comparator<StudDannie>() {
                        @Override
                        public int compare(StudDannie a, StudDannie b) {
                            // Преобразуйте версию аттестата к числовому значению, например, представив значения как 0 для Копия и 1 для Оригинал
                            int versionA = a.getАттестат().equals("Оригинал") ? 0 : 1;
                            int versionB = b.getАттестат().equals("Оригинал") ? 0 : 1;

                            if (versionA == versionB) {
                                // Если версии аттестата совпадают, вернуть результат сортировки по баллам
                                double scoreA = Double.parseDouble(a.getБалл().toString());
                                double scoreB = Double.parseDouble(b.getБалл().toString());
                                return Double.compare(scoreB, scoreA);
                            } else {
                                // Иначе, вернуть результат сортировки по версии аттестата
                                return Integer.compare(versionA, versionB);
                            }
                        }
                    });
                }

                // После сортировки списка, обновите ваш адаптер (если он используется)
                zayavkaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Можно оставить пустым, если не нужно обрабатывать событие
            }
        });


        zayavkaList = new ArrayList<>();
        zayavkaAdapter = new ZayavkaAdapter(zayavkaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(zayavkaAdapter);
        FirebaseDatabase.getInstance().getReference("Abiturient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zayavkaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudDannie studDannie = snapshot.getValue(StudDannie.class);
                    zayavkaList.add(studDannie);
                }
                Collections.reverse(zayavkaList);
                zayavkaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void получитьДанныеИзFirebase(String группа) {
        databaseReferenceAbiturient = FirebaseDatabase.getInstance().getReference("Abiturient");
        Query sortedQuery = databaseReferenceAbiturient.orderByChild("балл"); // Сортировка по полю "балл"

        sortedQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                zayavkaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudDannie studDannie = snapshot.getValue(StudDannie.class);
                    if (studDannie.getГруппа().equals(группа)) {
                        zayavkaList.add(studDannie);

                    }
                }

                Collections.sort(zayavkaList, new Comparator<StudDannie>() {
                    @Override
                    public int compare(StudDannie a, StudDannie b) {
                        // Преобразуйте баллы к числовому типу для сравнения
                        double scoreA = Double.parseDouble(a.getБалл().toString());
                        double scoreB = Double.parseDouble(b.getБалл().toString());

                        // Сортировка по убыванию балла
                        return Double.compare(scoreB, scoreA);
                    }
                });
                zayavkaAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если необходимо
                Toast.makeText(AdminActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedGroup = parent.getItemAtPosition(position).toString();
        получитьДанныеИзFirebase(selectedGroup);



    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}