package cd.hena.app_artisan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PersistenceSqlLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artisans.db";
    private static final int DATABASE_VERSION = 4;

    private static final String CREATE_TABLE_UTILISATEUR = "CREATE TABLE utilisateur (id INTEGER PRIMARY KEY AUTOINCREMENT, prenom TEXT, nom TEXT, email TEXT UNIQUE, password TEXT);";
    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE client (id INTEGER PRIMARY KEY AUTOINCREMENT, prenom TEXT, nom TEXT, adresse TEXT, telephone TEXT, date TEXT, type TEXT);";
    private static final String CREATE_TABLE_PRESTATION = "CREATE TABLE prestation (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT, categories TEXT, description TEXT, prix TEXT);";

    public PersistenceSqlLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_PRESTATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            //db.execSQL("DROP TABLE IF EXISTS client");
            db.execSQL(CREATE_TABLE_CLIENT);
        }
    }

    public long creerUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("prenom", utilisateur.getPrenom());
        values.put("nom", utilisateur.getNom());
        values.put("email", utilisateur.getEmail());
        values.put("password", utilisateur.getPassword());
        return db.insert("utilisateur", null, values);
    }
    public long creerPrestation(Prestation prestation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", prestation.getTitre());
        values.put("categories", prestation.getCategories());
        values.put("description", prestation.getDescription());
        values.put("prix", prestation.getPrix());
        return db.insert("prestation", null, values);
    }
    public long creerClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("prenom", client.getPrenom());
        values.put("nom", client.getNom());
        values.put("adresse", client.getAdresse());
        values.put("telephone", client.getTelephone());
        values.put("date", client.getDate());
        values.put("type", client.getType());
        return db.insert("client", null, values);
    }

    public boolean verifierConnexion(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("utilisateur", new String[]{"id"}, "email = ? AND password = ?", new String[]{email, password}, null, null, null);
        boolean isConnected = cursor.getCount() > 0;
        cursor.close();
        return isConnected;
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id",
                "prenom",
                "nom",
                "adresse",
                "telephone",
                "date",
                "type"
        };

        Cursor cursor = db.query(
                "client",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // ...
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int prenomIndex = cursor.getColumnIndex("prenom");
            int nomIndex = cursor.getColumnIndex("nom");
            int adresseIndex = cursor.getColumnIndex("adresse");
            int telephoneIndex = cursor.getColumnIndex("telephone");
            int dateIndex = cursor.getColumnIndex("date");
            int typeIndex = cursor.getColumnIndex("type");

            do {
                int id = cursor.getInt(idIndex);
                String prenom = cursor.getString(prenomIndex);
                String nom = cursor.getString(nomIndex);
                String adresse = cursor.getString(adresseIndex);
                String telephone = cursor.getString(telephoneIndex);
                String date = cursor.getString(dateIndex);
                String type = cursor.getString(typeIndex);

                Client client = new Client(prenom, nom, adresse, telephone, date, type);
                client.setId(id);
                clients.add(client);
            } while (cursor.moveToNext());
        }
// ...

        if (cursor != null) {
            cursor.close();
        }

        return clients;
    }
    public List<Prestation> getAllPrestation() {
        List<Prestation> prestations = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id",
                "titre",
                "categories",
                "description",
                "prix",

        };

        Cursor cursor = db.query(
                "prestation",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // ...
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int titreIndex = cursor.getColumnIndex("titre");
            int categoriesIndex = cursor.getColumnIndex("categories");
            int descriptionIndex = cursor.getColumnIndex("description");
            int prixIndex = cursor.getColumnIndex("prix");


            do {
                int id = cursor.getInt(idIndex);
                String titre = cursor.getString(titreIndex);
                String categories = cursor.getString(categoriesIndex);
                String description = cursor.getString(descriptionIndex);
                String prix = cursor.getString(prixIndex);


                Prestation prestation = new Prestation(titre, categories, description,prix);
                prestation.setId(id);
                prestations.add(prestation);
            } while (cursor.moveToNext());
        }
// ...

        if (cursor != null) {
            cursor.close();
        }

        return prestations;
    }

    public static class Utilisateur {
        private String prenom;
        private String nom;
        private String email;
        private String password;

        public Utilisateur(String prenom, String nom, String email, String password) {
            this.prenom = prenom;
            this.nom = nom;
            this.email = email;
            this.password = password;
        }

        public String getPrenom() {
            return prenom;
        }

        public String getNom() {
            return nom;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class Client {
        private int id;
        private String prenom;
        private String nom;
        private String adresse;
        private String telephone;
        private String date;
        private String type;

        public Client(String prenom, String nom, String adresse, String telephone, String date, String type) {
            this.prenom = prenom;
            this.nom = nom;
            this.adresse = adresse;
            this.telephone = telephone;
            this.date = date;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrenom() {
            return prenom;
        }

        public String getNom() {
            return nom;
        }

        public String getAdresse() {
            return adresse;
        }

        public String getTelephone() {
            return telephone;
        }

        public String getDate() {
            return date;
        }

        public String getType() {
            return type;
        }
    }

    public static class Prestation {
        private String titre;
        private String categories;
        private String description;
        private String prix;

        public Prestation(String titre, String categories, String description, String prix) {
            this.titre = titre;
            this.categories = categories;
            this.description = description;
            this.prix = prix;
        }

        public String getTitre() {
            return titre;
        }

        public String getCategories() {
            return categories;
        }

        public String getDescription() {
            return description;
        }

        public String getPrix() {
            return prix;
        }

        public void setId(int id) {

        }
    }
}