package ntk.ambrose.foodinspector.sampleview;

import android.graphics.Bitmap;

public class ListImageElement {
    private Bitmap bitmap;
    public ListImageElement(Bitmap bitmap){
        this.bitmap=bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
