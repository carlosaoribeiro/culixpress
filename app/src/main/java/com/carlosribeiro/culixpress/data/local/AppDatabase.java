package com.carlosribeiro.culixpress.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    // ✅ Adicionando Migração da versão 1 para a versão 2
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 🔥 Exemplo: Alteração na tabela de usuários
            database.execSQL("ALTER TABLE users ADD COLUMN phoneNumber TEXT DEFAULT ''");
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "culixpress_database")
                            .addMigrations(MIGRATION_1_2) // ✅ Adicionando a migração
                            .fallbackToDestructiveMigration() // 🔥 Remove todos os dados se não puder migrar
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
