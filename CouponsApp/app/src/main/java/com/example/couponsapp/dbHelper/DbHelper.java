package com.example.couponsapp.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "cupones.s3db";
    private static final int DATABASEVERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS DIRECCION(" +
                "ID_DIRECCION INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "MUNICIPIO VARCHAR(40) NOT NULL, \n" +
                "CALLE VARCHAR(40) NOT NULL, \n" +
                "NUMERO_LOCAL VARCHAR(7))");

        db.execSQL("CREATE TABLE IF NOT EXISTS RESTAURANTE(" +
                "ID_RESTAURANTE INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_DIRECCION INTEGER NOT NULL, \n" +
                "NOMBRE_RESTAURANTE VARCHAR(40) NOT NULL, \n" +
                "UNIQUE(ID_RESTAURANTE, ID_DIRECCION))");

        db.execSQL("CREATE TABLE IF NOT EXISTS TIPOCUPON(" +
                "ID_TIPO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "NOMBRE_TIPO VARCHAR(40) NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS CUPON(" +
                "ID_CUPON INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_RESTAURANTE INTEGER NOT NULL, \n" +
                "ID_TIPO INTEGER NOT NULL, \n" +
                "CODIGO_CUPON VARCHAR(5) NOT NULL UNIQUE, \n" +
                "NOMBRE_CUPON VARCHAR(35) NOT NULL, \n" +
                "DESCRIPCION_CUPON VARCHAR(50) NOT NULL, \n" +
                "HORARIO_CUPON VARCHAR(35) NOT NULL, \n" +
                "DISPONIBLE INTEGER NOT NULL," +
                "UNIQUE(ID_CUPON, ID_RESTAURANTE, ID_TIPO))");

        db.execSQL("CREATE TABLE IF NOT EXISTS USUARIO(" +
                "ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "USERNAME VARCHAR(40) NOT NULL, \n" +
                "PASSWORD VARCHAR(40) NOT NULL, \n" +
                "EMAIL VARCHAR(40) NOT NULL, \n" +
                "UNIQUE(USERNAME, EMAIL))");

        db.execSQL("CREATE TABLE IF NOT EXISTS CLIENTE(" +
                "ID_CLIENTE INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_USUARIO INTEGER, \n" +
                "NOMBRE_CLIENTE VARCHAR(40) NOT NULL, \n" +
                "APELLIDO_CLIENTE VARCHAR(40) NOT NULL, \n" +
                "SEXO_CLIENTE VARCHAR(1) NOT NULL, \n" +
                "UNIQUE(ID_CLIENTE, ID_USUARIO))");

        db.execSQL("CREATE TABLE IF NOT EXISTS CUPONESDECLIENTE(" +
                "ID_CUPON_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_CUPON INTEGER NOT NULL, \n" +
                "ID_CLIENTE INTEGER NOT NULL, \n" +
                "FECHA_CANJEO VARCHAR(35) NOT NULL, \n" +
                "UNIQUE(ID_CUPON_USUARIO, ID_CUPON, ID_CLIENTE))");



        /*              TRIGGERS              */

        db.execSQL("CREATE TRIGGER FK_RESTAURANTE_DIRECCION BEFORE INSERT " +
                "ON RESTAURANTE " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT ID_DIRECCION FROM DIRECCION WHERE ID_DIRECCION = NEW.ID_DIRECCION) IS NULL) " +
                "THEN RAISE(ABORT, 'No existe la dirección!') " +
                "END; " +
                "END; ");


        db.execSQL("CREATE TRIGGER FK_CUPON_RESTAURANTE BEFORE INSERT " +
                "ON CUPON " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT ID_RESTAURANTE FROM RESTAURANTE WHERE ID_RESTAURANTE = NEW.ID_RESTAURANTE) IS NULL) " +
                "THEN RAISE(ABORT, 'No existe el restaurante!') " +
                "END; " +
                "END; ");

        db.execSQL("CREATE TRIGGER FK_CUPON_TIPO BEFORE INSERT " +
                "ON CUPON " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT ID_TIPO FROM TIPOCUPON WHERE ID_TIPO = NEW.ID_TIPO) IS NULL) " +
                "THEN RAISE(ABORT, 'No existe el tipo de cupon!') " +
                "END; " +
                "END; ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS DIRECCION");
        db.execSQL("DROP TABLE IF EXISTS RESTAURANTE");
        db.execSQL("DROP TABLE IF EXISTS TIPOCUPON");
        db.execSQL("DROP TABLE IF EXISTS CUPON");
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS CLIENTE");
        db.execSQL("DROP TABLE IF EXISTS CUPONESDECLIENTE");
    }
}
