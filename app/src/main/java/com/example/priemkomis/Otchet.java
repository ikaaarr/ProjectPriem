package com.example.priemkomis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Otchet extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_WRITE_STORAGE = 112;

    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otchet);
        ImageView imageView = findViewById(R.id.imageViewPodacha);
        databaseRef = FirebaseDatabase.getInstance().getReference("Abiturient");
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDataToExcel();
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Otchet.this,PodachaAct.class);
                startActivity(intent);
            }
        });


    }
    private void exportDataToExcel() {
        // Получите данные из Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Abiturient");
        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<List<Object>> values = new ArrayList<>();

                // Добавляем заголовки столбцов
                List<Object> headers = new ArrayList<>();
                headers.add("ФИО");
                headers.add("Номер телефона");
                headers.add("Балл");
                headers.add("Аттестат");
                headers.add("Группа");
                headers.add("Доп.группа");
                values.add(headers);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<Object> row = new ArrayList<>();
                    row.add(snapshot.child("ФИО").getValue().toString());
                    row.add(snapshot.child("Номер_телефона").getValue().toString());
                    row.add(snapshot.child("Балл").getValue().toString());
                    row.add(snapshot.child("Аттестат").getValue().toString());
                    row.add(snapshot.child("Группа").getValue().toString());
                    row.add(snapshot.child("ДопГруппа").getValue().toString());
                    values.add(row);
                }

                // Экспорт данных в Excel файл
                writeToExcelFileAndSendEmail(values);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Otchet.this, "Failed to retrieve data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeToExcelFileAndSendEmail(List<List<Object>> values) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        int rowNum = 0;
        for (List<Object> rowValues : values) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (Object value : rowValues) {
                Cell cell = row.createCell(cellNum++);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } // Add other data types if necessary
            }
        }

        String folderName = Environment.DIRECTORY_DOCUMENTS;
        File folder = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                Log.e("Folder Creation", "Failed to create folder");
                return;
            }
        }

        String filePath = folder.getAbsolutePath() + "/data.xlsx";

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            Toast.makeText(Otchet.this, "Отчет сохранен " + filePath, Toast.LENGTH_SHORT).show();
            sendEmailWithAttachment(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Otchet.this, "Error creating Excel file", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmailWithAttachment(String filePath) {
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        String email = editTextEmail.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/octet-stream");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Отчет");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Отчет во вложении");

        // Получение URI файла через FileProvider
        Uri fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(filePath));
        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(Intent.createChooser(emailIntent, "Отправка отчета..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Otchet.this, "Не найдено приложение для отправки электронной почты.", Toast.LENGTH_SHORT).show();
        }
    }


}