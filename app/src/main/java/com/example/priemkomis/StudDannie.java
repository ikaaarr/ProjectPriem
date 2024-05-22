package com.example.priemkomis;

public class StudDannie {
    private String ФИО;
    private String Номер_телефона;
    private String Балл;
    private String Аттестат;
    private String Группа;
    private String ДопГруппа;

public String getДопГруппа(){
    return ДопГруппа;
}
public void setДопГруппа(String допГруппа){
    this.ДопГруппа = допГруппа;
}
    public String getФИО() {
        return ФИО;
    }

    public void setФИО(String ФИО) {
        this.ФИО = ФИО;
    }

    public String getНомер_телефона() {
        return Номер_телефона;
    }

    public void setНомер_телефона(String номер_телефона) {
        this.Номер_телефона = номер_телефона;
    }

    public CharSequence getБалл() {
        return Балл;
    }

    public void setБалл(float Балл) {
       this.Балл = String.valueOf(Балл);
   }

    public String getАттестат() {
        return Аттестат;
    }

    public void setАттестат(String аттестат) {
        this.Аттестат = аттестат;
    }

    public String getГруппа() {
        return Группа;
    }

    public void setГруппа(String группа) {
        this.Группа = группа;
    }


    public String getAverageScore() {
        return null;
    }

    public String getScore() {
        return Балл;
    }
}
