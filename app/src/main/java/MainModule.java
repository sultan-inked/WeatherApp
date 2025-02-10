import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.R;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

//@Module
//@InstallIn(SingletonComponent.class)
public abstract class MainModule {
//    @Provides
//    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}