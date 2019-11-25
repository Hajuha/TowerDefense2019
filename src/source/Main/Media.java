package source.Main;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Media {
    private AudioClip clickMedia = new AudioClip(new File("src/res/Audio/clickSound.mp3").toURI().toString());

    public AudioClip getClickMedia() {
        return clickMedia;
    }
}
