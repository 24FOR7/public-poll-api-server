package twentyfourFORseven.publicpoll.Configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.sdk.path}")   //경로 가져오기
    private String firebaseSdkPath;

    private FirebaseApp firebaseApp;

    /**
     * Bean이 생성되기 전에 PostConstruct를 사용하여 firebase.json을 읽고 설정
     */
    @PostConstruct
    public FirebaseApp initializeFCM() throws IOException {
//        파일 버전
        FileInputStream fis = new FileInputStream(firebaseSdkPath);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(fis)).build();

//        환경변수 버전
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.getApplicationDefault()).build();
        firebaseApp = FirebaseApp.initializeApp(options);
        return firebaseApp;
    }

    @Bean
    public FirebaseAuth initFirebaseAuth(){
        FirebaseAuth instance = FirebaseAuth.getInstance(firebaseApp);
        return instance;
    }
}
